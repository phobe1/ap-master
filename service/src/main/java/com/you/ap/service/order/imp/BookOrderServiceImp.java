package com.you.ap.service.order.imp;

import com.google.common.collect.Sets;
import com.you.ap.common.helper.DateUtil;
import com.you.ap.common.helper.StringUtil;
import com.you.ap.dao.order.IBookOrderDAO;
import com.you.ap.domain.enums.order.OrderStatusEnum;
import com.you.ap.domain.enums.user.UserTypeEnum;
import com.you.ap.domain.form.book.BookForm;
import com.you.ap.domain.model.TokenModel;
import com.you.ap.domain.pojo.Student;
import com.you.ap.domain.schema.book.BookDayDetailDO;
import com.you.ap.domain.schema.order.BookOrderDO;
import com.you.ap.domain.schema.user.TeacherInfoDO;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.domain.vo.order.BookOrderVO;
import com.you.ap.service.book.IBookLocationService;
import com.you.ap.service.book.IStudentBookService;
import com.you.ap.service.order.IBookOrderService;
import com.you.ap.service.teachcourse.ICourseService;
import com.you.ap.service.user.IStudentInfoService;
import com.you.ap.service.user.ITeacherInfoService;
import com.you.ap.service.user.IUserOnlineInfoService;
import com.you.ap.service.wallet.IWalletService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static java.util.stream.Collectors.toList;

@Service
public class BookOrderServiceImp implements IBookOrderService {

    private Logger logger= Logger.getLogger(BookOrderServiceImp.class);

    @Autowired private IUserOnlineInfoService userOnlineInfoService;

    @Resource private IStudentBookService studentBookService;

    @Autowired private ICourseService courseService;

    @Autowired private ITeacherInfoService teacherInfoService;

    @Autowired private IStudentInfoService studentInfoService;

    @Autowired private IBookLocationService locationService;

    @Autowired private IWalletService walletService;

    @Autowired
    private IBookOrderDAO bookOrderDAO;

    @Override
    public BookOrderDO getById(int id) {
        return bookOrderDAO.getById(id);
    }

    @Override
    public ApiResponse createBookOrder(BookForm bookForm, BookDayDetailDO bookDayDetailDO, int studentId) {
        try{
            if(bookForm==null || bookDayDetailDO == null){
                return null;
            }
            TeacherInfoDO teacherInfoDO = teacherInfoService.getById(bookForm.getTeacherId());
            if (teacherInfoDO == null){
                throw new RuntimeException("teacher is not exit");
            }
            BookOrderDO bookOrderDO=new BookOrderDO(bookForm,bookDayDetailDO,teacherInfoDO,studentId);
            Set<Integer> houBoxSets = StringUtil.splitStr(bookForm.getHourBoxs());
            bookOrderDO.setMoney(houBoxSets.size()*60*bookOrderDO.getPricePerMinute());
            int maxHour = Sets.newTreeSet(houBoxSets).last();
            bookOrderDO.setFinishBookOrderTime(DateUtil.formateDateToYMDHMS(DateUtil.createDate(bookOrderDO.getBookDay(),maxHour+1)));
            bookOrderDAO.addBookOrder(bookOrderDO);
            return ApiResponse.buildSuccess(createBookOrderVO(bookOrderDO,UserTypeEnum.Teacher));
        }catch (Exception e){
            logger.error("createBookOrder",e);
            throw new RuntimeException();
        }
    }

    @Override
    public List<Integer> closeOutTimeOrders(String end) {
        List<Integer> idLists = bookOrderDAO.getOrderIdListByTime(end,OrderStatusEnum.NOT_PAY.getKey());
        idLists.forEach(id->closeOrderById(id));
        return idLists;
    }

    @Override
    public void closeOrderById(int id) {
        try{
            BookOrderDO bookOrderDO = getById(id);
            if (bookOrderDO.getStatus()!= OrderStatusEnum.NOT_PAY.getKey()){
                return;
            }
            if(studentBookService.cancelBook(bookOrderDO.getBookDayId(),bookOrderDO.getHourBoxs())){
                bookOrderDAO.updateStatus(bookOrderDO.getId(),OrderStatusEnum.CANCELD.getKey());
            }
        }catch (Exception e){
            logger.error(e);
        }
    }

    @Override
    public ApiResponse payOrder(int studentId,int  orderId) {
        try{
            BookOrderDO orderDO= getById(orderId);
            if (orderDO == null || orderDO.getStudentId() != studentId ||
                    orderDO.getStatus() != OrderStatusEnum.NOT_PAY.getKey()){
                return ApiResponse.buildFailure("订单无效");
            }
            walletService.transCoin(studentId,orderDO.getTeacherId(),Math.round(orderDO.getMoney()));
            bookOrderDAO.updateStatus(orderDO.getId(),OrderStatusEnum.PAY_FINISH.getKey());
            return  ApiResponse.buildSuccess("支付成功");
        }catch (Exception e){
            return ApiResponse.buildFailure(e.getMessage());
        }
    }

    @Override
    public List<Integer> finishOrder(String end) {
        List<Integer> idLists = bookOrderDAO.getOrderIdListByFinishTime(end,OrderStatusEnum.PAY_FINISH.getKey());
        idLists.forEach(id->bookOrderDAO.updateStatus(id,OrderStatusEnum.ORDER_FINISH.getKey()));
        return idLists;
    }

    @Override
    public ApiResponse closeNotPayOrder(int id, TokenModel tokenModel) {
        try{
            if (tokenModel==null){
                return ApiResponse.buildFailure("token is null");
            }
            BookOrderDO bookOrderDO = getById(id);
            if (bookOrderDO==null ||
                    bookOrderDO.getStatus()!= OrderStatusEnum.NOT_PAY.getKey()
                    ||(tokenModel.getUserType()== UserTypeEnum.Teacher&&bookOrderDO.getTeacherId()!=tokenModel.getUserId())
                    ||(tokenModel.getUserType()== UserTypeEnum.Student&&bookOrderDO.getStudentId()!=tokenModel.getUserId())){
                return ApiResponse.buildFailure("opt error");
            }
            closeOrderById(id);
            return ApiResponse.buildSuccess("取消成功");
        }catch (Exception e){
            return ApiResponse.buildFailure(e.getMessage());
        }
    }

    @Override
    public ApiResponse getOrderList(TokenModel tokenModel,Set<Integer> status,int index,int limit) {
        if (tokenModel == null){
            return ApiResponse.buildFailure("token is valid");
        }
        return ApiResponse.buildSuccess( (tokenModel.getUserType()== UserTypeEnum.Student ?
                bookOrderDAO.getOrderListByStudent(tokenModel.getUserId(),status,index,limit):
                bookOrderDAO.getOrderListByTeacher(tokenModel.getUserId(),status,index,limit)).
                stream().
                map(bookOrderDO -> createBookOrderVO(bookOrderDO,UserTypeEnum.notValueOf(tokenModel.getUserType().key))).collect(toList()));
    }

    @Override
    public ApiResponse checkHasPaid(int orderId, int studentId) {
        BookOrderDO bookOrderDO = getById(orderId);
        if (bookOrderDO == null || bookOrderDO.getStudentId()!=studentId){
            return ApiResponse.buildFailure("invalid orderId ");
        }
        return ApiResponse.buildSuccess(OrderStatusEnum.valueOf(bookOrderDO.getStatus()));
    }

    private BookOrderVO createBookOrderVO(BookOrderDO bookOrderDO,UserTypeEnum userTypeEnum){
        if(bookOrderDO == null){
            return  null;
        }
        BookOrderVO bookOrderVO = new BookOrderVO(bookOrderDO).of(courseService.getById(bookOrderDO.getCourseId())).
                of(locationService.getLocationByChild(bookOrderDO.getLocationId())).
                of(userOnlineInfoService.getByUser(bookOrderDO.getTeacherId(),UserTypeEnum.Teacher));
        if(userTypeEnum == UserTypeEnum.Student){
            bookOrderVO.of(studentInfoService.getInfoById(bookOrderDO.getStudentId()));
        }else{
            bookOrderVO.of(teacherInfoService.getById(bookOrderDO.getTeacherId()));
        }
        return  bookOrderVO;
    }

}
