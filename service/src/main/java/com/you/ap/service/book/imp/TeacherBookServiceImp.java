package com.you.ap.service.book.imp;

import com.you.ap.common.helper.DateUtil;
import com.you.ap.common.helper.StringUtil;
import com.you.ap.dao.book.IBookTeachDAO;
import com.you.ap.domain.form.book.BookForm;
import com.you.ap.domain.model.book.BookDayDetailModel;
import com.you.ap.domain.schema.book.BookDayDetailDO;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.domain.vo.book.BookDayVO;
import com.you.ap.domain.vo.book.BookDayDetailVO;
import com.you.ap.service.book.IBookLocationService;
import com.you.ap.service.book.IBookService;
import com.you.ap.service.book.ITeacherBookService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import static java.util.stream.Collectors.toList;

/*
@Created by gaoxiaoning 2017/12/14
 */
@Service
public class TeacherBookServiceImp implements ITeacherBookService {
    private Logger logger= Logger.getLogger(TeacherBookServiceImp.class);

    @Autowired private IBookService bookService;
    @Autowired
    private IBookTeachDAO bookDao;

    @Autowired private IBookLocationService bookLocationService;

    @Override
    public ApiResponse getBookDayListByTeacherId(int teacherId){
        try{
            int beginDay = DateUtil.formateDateToYMDIntDay(new Date());
            int endDay = DateUtil.formateDateToYMDIntDay(DateUtil.addDays(new Date(),30));
            List<BookDayDetailDO> bookTeachDayDOList =bookDao.getBookDayListByTeacherId(teacherId,beginDay,endDay);
            return ApiResponse.buildSuccess(bookTeachDayDOList.stream().map(bookTeachDayDO -> new BookDayVO(bookTeachDayDO,null)).
                    collect(toList()));
        }catch (Exception e){
            logger.error("getBookDayListByTeacherId",e);
            return ApiResponse.buildFailure(e.getMessage());
        }
    }

    @Override
    public ApiResponse getBookDayDetailList(int id,int teacherId) {
       try{
           BookDayDetailDO bookDayDetailDO = bookService.getById(id);
           if (bookDayDetailDO == null || bookDayDetailDO.getTeacherId() != teacherId){
               return ApiResponse.buildFailure("bookid is not yours!!");
           }
        BookDayDetailModel bookDayDetailModel = bookService.converDO2Model(bookDayDetailDO);
        if ( bookDayDetailModel ==  null){
            return ApiResponse.buildFailure("error id");
        }
        return ApiResponse.buildSuccess(new BookDayDetailVO(bookDayDetailModel).of(bookLocationService.getLocationByChild(bookDayDetailModel.getLocationId())));
       }catch (Exception e){
           logger.error("getBookHourBoxList",e);
           return  ApiResponse.buildFailure(e.getMessage());
       }
    }

    @Override
    public ApiResponse publishBookTimes(BookForm bookForm,int teacherId) {
        try{
            if (bookForm == null){
                return ApiResponse.buildFailure("param lost");
            }
            BookDayDetailDO bookDayDetailDO = bookService.getById(bookForm.getBookDayId());
            if (bookDayDetailDO == null || bookDayDetailDO.getTeacherId()!= teacherId){
                return ApiResponse.buildFailure("bookid is not yours!!");
            }
            BookDayDetailModel bookDayDetailModel = bookService.converDO2Model(bookDayDetailDO);
            if (bookDayDetailModel == null){
                return ApiResponse.buildFailure("bookDayId is null");
            }
            bookDayDetailModel.publishBoxHours(StringUtil.splitStr(bookForm.getHourBoxs()),bookForm.getPositionId());
            bookService.updateBookDay(bookDayDetailDO,bookDayDetailModel);
            return ApiResponse.buildSuccess("update successful");
        }catch (Exception e){
            logger.error("publishBookTimes",e);
            return ApiResponse.buildFailure(e.getMessage());
        }
    }

}
