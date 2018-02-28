package com.you.ap.service.pay;

import com.you.ap.domain.model.PayModel;

public interface IPayService {

    void afterPay(PayModel payModel);
}
