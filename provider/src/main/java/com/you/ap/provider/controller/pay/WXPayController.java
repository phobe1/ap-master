package com.you.ap.provider.controller.pay;

import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.service.pay.IWXPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

@Controller
@RequestMapping("/pay/wx")
public class WXPayController {

    @Autowired private IWXPayService iwxPayService;

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value="/recharge",method = RequestMethod.GET)
    @ResponseBody
    public Object recharge(
            @RequestAttribute(value="studentId") int studentId,
            @PathVariable(value="money") int money
    ){
        return ApiResponse.buildSuccess(iwxPayService.recharge(studentId,money));
    }




}
