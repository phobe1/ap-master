package com.you.ap.provider.controller.order;


import com.you.ap.common.helper.StringUtil;
import com.you.ap.domain.model.TokenModel;
import com.you.ap.service.order.IBookOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/book/order")
public class BookOrderController {

    @Autowired private  IBookOrderService bookOrderService;

    @RequestMapping(value = "/all.json", method = RequestMethod.GET)
    @ResponseBody
    public Object getOrderListByStudentList(
            @RequestAttribute("all") TokenModel tokenModel,
            @RequestParam("index") int index,
            @RequestParam("limit") int limit,
            @RequestParam("status") String status
    ){
       return  bookOrderService.getOrderList(tokenModel, StringUtil.splitStr(status),index,limit);
    }

    @RequestMapping(value = "/cancel/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public Object cancel(
            @RequestAttribute("all") TokenModel tokenModel,
            @PathVariable("orderId") int orderId
    ){
        return  bookOrderService.closeNotPayOrder(orderId,tokenModel);
    }

    @RequestMapping(value = "/check/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public Object checkHasPaid(
            @RequestAttribute("studentId") int studentId,
            @PathVariable("orderId") int orderId
    ){
        return  bookOrderService.checkHasPaid(orderId,studentId);
    }

    @RequestMapping(value = "/pay/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public Object pay(
            @RequestAttribute("studentId") int studentId,
            @PathVariable("orderId") int orderId
    ){
        return  bookOrderService.payOrder(studentId,orderId);
    }
}
