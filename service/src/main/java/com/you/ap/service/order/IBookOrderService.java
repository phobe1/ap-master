package com.you.ap.service.order;

import com.you.ap.domain.form.book.BookForm;
import com.you.ap.domain.model.TokenModel;
import com.you.ap.domain.schema.book.BookDayDetailDO;
import com.you.ap.domain.schema.order.BookOrderDO;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.domain.vo.order.BookOrderVO;
import com.you.ap.service.IBaseService;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface IBookOrderService extends IBaseService<BookOrderDO> {

    List<Integer> closeOutTimeOrders(String end);

    void closeOrderById(int id);

    ApiResponse payOrder(int studentId,int  orderId);

    List<Integer> finishOrder(String end);

    ApiResponse createBookOrder(BookForm bookForm, BookDayDetailDO bookDayDetailDO, int studentId);

    ApiResponse closeNotPayOrder(int id,TokenModel tokenModel);

    ApiResponse getOrderList(TokenModel tokenModel, Set<Integer> status, int index, int limit);

    ApiResponse checkHasPaid(int orderId,int studentId);


}
