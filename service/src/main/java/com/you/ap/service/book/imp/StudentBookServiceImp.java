package com.you.ap.service.book.imp;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.you.ap.common.helper.DateUtil;
import com.you.ap.common.helper.StringUtil;
import com.you.ap.dao.book.IBookTeachDAO;
import com.you.ap.domain.enums.user.UserTypeEnum;
import com.you.ap.domain.form.book.BookForm;
import com.you.ap.domain.model.book.BookDayDetailModel;
import com.you.ap.domain.model.book.LocationModel;
import com.you.ap.domain.schema.book.BookDayDetailDO;
import com.you.ap.domain.schema.teachcourse.CourseDO;
import com.you.ap.domain.schema.user.TeacherInfoDO;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.domain.vo.book.*;
import com.you.ap.service.book.IBookLocationService;
import com.you.ap.service.book.IBookService;
import com.you.ap.service.book.IStudentBookService;
import com.you.ap.service.order.IBookOrderService;
import com.you.ap.service.teachcourse.ITeachCourseService;
import com.you.ap.service.user.ITeacherInfoService;
import com.you.ap.service.user.IUserOnlineInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static java.util.stream.Collectors.toList;

/*
@Created by gaoxiaoning 2017/12/14
 */
@Service
public class StudentBookServiceImp implements IStudentBookService {

    @Autowired private ITeacherInfoService teacherInfoService;

    @Autowired private IBookLocationService locationService;

    @Autowired private ITeachCourseService teachCourseService;

    @Autowired private IBookService bookService;

    @Autowired private IBookOrderService bookOrderService;

    @Autowired private IUserOnlineInfoService userOnlineInfoService;

    @Autowired
    private IBookTeachDAO bookDao;

    private Logger logger= LoggerFactory.getLogger(StudentBookServiceImp.class);


    @Override
    public ApiResponse getTeacherBookInfoByTeacherId(int teacherId) {
        try{
            TeacherInfoDO teacherInfoDO = teacherInfoService.getById(teacherId);
            if (teacherInfoDO == null ){
                return ApiResponse.buildFailure("teacherId is not exit");
            }
            List<CourseDO> courseDOList = teachCourseService.getCourseListByTeacherId(teacherId);
            int startDay = DateUtil.formateDateToYMDIntDay(new Date());
            int endDay = DateUtil.formateDateToYMDIntDay(DateUtil.addDays(new Date(),30));
            List<Integer> positionList = bookDao.getLocationListOfTeacher(teacherId,startDay,endDay);
            positionList.remove(null);
            positionList.remove(new Integer(0));
            List<LocationModel> locationDOList = Sets.newHashSet(positionList).stream().map(id->locationService.getLocationByChild(id)).collect(toList());
            JSONObject js=locationService.getReversonJson(locationDOList);
            TeacherInfoForBookVO teacherInfoForBookVO=new TeacherInfoForBookVO(courseDOList,locationDOList,teacherInfoDO,userOnlineInfoService.getByUser(teacherInfoDO.getId(), UserTypeEnum.Teacher));
            teacherInfoForBookVO.setJs(js);
            return ApiResponse.buildSuccess(teacherInfoForBookVO);

        }catch (Exception e){
            logger.error("getTeacherBookInfoByTeacherId",e);
            return ApiResponse.buildFailure(e.getMessage());
        }
    }

    @Override
    public ApiResponse getBookDayListByTeacherId(int teacherId,int positionId) {
        try{
            int beginDay = DateUtil.formateDateToYMDIntDay(new Date());
            int endDay = DateUtil.formateDateToYMDIntDay(DateUtil.addDays(new Date(),30));
            List<BookDayDetailDO> bookTeachDayDOList =bookDao.getBookDayListByTeacherId(teacherId,beginDay,endDay);
            return ApiResponse.buildSuccess(bookTeachDayDOList.stream().
                    map(bookTeachDayDO -> new BookDayVO(bookTeachDayDO,positionId)).
                    collect(toList()));
        }catch (Exception e){
            logger.error("getBookDayListByTeacherId",e);
            return ApiResponse.buildFailure(e.getMessage());
        }
    }

    @Override
    public ApiResponse getBookDayDetailList(int bookDayId,int teacherId) {
        try{
            BookDayDetailDO bookDayDetailDO = bookService.getById(bookDayId);
            if (bookDayDetailDO == null || bookDayDetailDO.getTeacherId() != teacherId){
                return ApiResponse.buildFailure("teacherid and bookdayid is not mapped!");
            }
            BookDayDetailModel bookDayDetailModel = bookService.converDO2Model(bookDayDetailDO);
            return ApiResponse.buildSuccess(new BookDayDetailVO(bookDayDetailModel).of(locationService.getLocationByChild(bookDayDetailModel.getLocationId())));
        }catch (Exception e){
            logger.error("getBookHourBoxList",e);
            return  ApiResponse.buildFailure(e.getMessage());
        }
    }

    @Transactional
    @Override
    public synchronized ApiResponse book(BookForm bookForm, int studentId) {
        try{

            if(teachCourseService.checkIsValid(bookForm.getTeacherId(),bookForm.getCourseId())==null){
                return ApiResponse.buildFailure("该老师课程不存在");
            }
            Set<Integer> applyIdSet = StringUtil.splitStr(bookForm.getHourBoxs());
            BookDayDetailDO bookDayDetailDO = bookService.getById(bookForm.getBookDayId());
            if (bookDayDetailDO== null || bookDayDetailDO.getTeacherId() != bookForm.getTeacherId()){
                return ApiResponse.buildFailure("预约的日期ID和教师不匹配!");
            }
            TreeSet<Integer> treeHourbox = Sets.newTreeSet(applyIdSet);
            Date early = DateUtil.createDate(bookDayDetailDO.getBookDay(),treeHourbox.first());
            if (early == null || early.before(new Date())){
                return  ApiResponse.buildFailure("不能预约当前之前之前的课程");
            }
            BookDayDetailModel bookDayDetailModel = bookService.converDO2Model(bookDayDetailDO);
            if(bookDayDetailModel.checkCanBook(applyIdSet) == false){
                return ApiResponse.buildFailure(" 您选择的时间段已经被其他学生预定！！");
            }
            bookDayDetailModel.applyBook(applyIdSet);
            bookService.updateBookDay(bookDayDetailDO,bookDayDetailModel);
            return bookOrderService.createBookOrder(bookForm,bookDayDetailDO,studentId);
        }catch (Exception e){
            logger.error("book",e);
            return ApiResponse.buildFailure(e.getMessage());
        }
    }

    @Override
    public boolean cancelBook(int bookDayId,String hourBoxs) {
        try{
            BookDayDetailDO bookDayDetailDO = bookService.getById(bookDayId);
            BookDayDetailModel bookDayDetailModel = bookService.converDO2Model(bookDayDetailDO);
            if(bookDayDetailModel == null) {
                return false;
            }
            bookDayDetailModel.cancelBook(StringUtil.splitStr(hourBoxs));
            bookService.updateBookDay(bookDayDetailDO,bookDayDetailModel);
            return true;
        }catch (RuntimeException e) {
            logger.error(e.getMessage());
        }catch (Exception e){
            logger.error("cancelBook",e);
        }
        return false;
    }


}
