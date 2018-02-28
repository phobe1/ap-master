package com.you.ap.service.book;

import com.you.ap.domain.form.book.BookForm;
import com.you.ap.domain.vo.ApiResponse;

/*
@Created by gaoxiaoning 2017/12/14
 */
public interface ITeacherBookService {

    ApiResponse getBookDayListByTeacherId(int teacherId);

    ApiResponse getBookDayDetailList(int id,int teacherId);

    ApiResponse publishBookTimes(BookForm bookForm,int teacherId);

}
