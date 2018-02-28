package com.you.ap.service.pay;

import com.you.ap.domain.model.PayModel;
import com.you.ap.domain.vo.ApiResponse;

import java.util.Map;

public interface IWXPayService {

    ApiResponse recharge(int studentId,int money);

    String checkPayStatus(String xmlData);
}
