package com.you.ap.service.book;

import com.you.ap.domain.form.book.BookForm;
import com.you.ap.domain.schema.order.BookOrderDO;
import com.you.ap.domain.vo.ApiResponse;
/*
@Created by gaoxiaoning 2017/12/14
 */
public interface IStudentBookService {

     ApiResponse getTeacherBookInfoByTeacherId(int teacherId);

     ApiResponse getBookDayListByTeacherId(int teacherId,int positionId);

     ApiResponse getBookDayDetailList(int bookDayId,int teacherId);

     ApiResponse book(BookForm bookForm, int studentId);

     boolean cancelBook(int bookDayId,String hourBoxs);


}
