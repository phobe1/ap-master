package com.you.ap.service.book;

/*
@Created by gaoxiaoning 2017/12/14
 */
import com.you.ap.domain.model.book.BookDayDetailModel;
import com.you.ap.domain.schema.book.BookDayDetailDO;
import com.you.ap.service.IBaseService;

import java.util.Date;

public interface IBookService extends IBaseService<BookDayDetailDO> {

	BookDayDetailModel converDO2Model(BookDayDetailDO bookDayDetailDO);

	void updateBookDay(BookDayDetailDO bookDayDetailDO,BookDayDetailModel bookDayDetailModel);

	void autoAddBookDay(int teacherId);

}
