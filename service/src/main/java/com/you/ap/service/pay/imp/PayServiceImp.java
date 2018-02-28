package com.you.ap.service.pay.imp;

import com.you.ap.domain.enums.user.UserTypeEnum;
import com.you.ap.domain.model.PayModel;
import com.you.ap.domain.schema.order.TradeDO;
import com.you.ap.service.pay.IPayService;
import com.you.ap.service.wallet.ITradeService;
import com.you.ap.service.wallet.IWalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImp implements IPayService {
    @Autowired private IWalletService walletService;
    @Autowired private ITradeService tradeService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void afterPay(PayModel payModel) {

        if (payModel == null){
            return ;
        }
        String body = payModel.getBody();
        if(body== null || !body.contains("#")){
            return ;
        }
        String num[] = body.split("#");
        if(num.length<1){
            return;
        }
        logger.info("给用户"+Integer.valueOf(num[0])+"充值"+Integer.valueOf(payModel.getTotalFee())*0.1+"coin");
        //更新钱包余额
        walletService.addCoin(Integer.valueOf(num[0]), UserTypeEnum.Student.key,(int)(Integer.valueOf(payModel.getTotalFee())*0.1));
        //更新交易流水状态
        TradeDO tradeDO=tradeService.selectByOrderNumber(payModel.getOrderNumber());
        if(tradeDO!=null){
            tradeDO.setStatus(3);
            tradeService.updateByPrimaryKeySelective(tradeDO);
        }


    }
}
