package com.you.ap.service.pay.imp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.you.ap.common.constant.WXConstants;
import com.you.ap.domain.model.PayModel;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.service.order.IBookOrderService;
import com.you.ap.service.pay.IPayService;
import com.you.ap.service.pay.IWXPayService;
import com.you.ap.service.wallet.ITradeService;
import com.you.ap.service.wallet.IWalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class WXPayServiceImp implements IWXPayService {
    private Logger logger = LoggerFactory.getLogger(WXPayServiceImp.class);
    @Autowired private IBookOrderService bookOrderService;
    @Autowired private WXPay wxPay;
    @Autowired private ITradeService tradeService;
    @Autowired private IPayService payService;



    @Override
    public ApiResponse recharge(int studentId, int money) {
        try{
            return ApiResponse.buildSuccess(createPayOrder(new PayModel(studentId,money)));
        }catch (Exception e){
            return  ApiResponse.buildFailure(e.getMessage());
        }
    }

    @Override
    public String checkPayStatus(String xmlData) {
        logger.info("checkPayStatus");
        logger.info(xmlData);
        try{
            logger.info("0");
            Map<String,String> notifyMap = WXPayUtil.xmlToMap(xmlData);
            logger.info("1");
            if(wxPay.isPayResultNotifySignatureValid(notifyMap) &&
                    "SUCCESS".equals(notifyMap.get("result_code"))){
                //Map<String,String> attach = WXPayUtil.xmlToMap(notifyMap.get("attach"));
                String attach=notifyMap.get("attach");
                logger.info("2");
                PayModel payModel = new PayModel();
                payModel.setTotalFee(notifyMap.get("total_fee"));
                payModel.setBody(attach);
                payModel.setOrderNumber(notifyMap.get("out_trade_no"));
                logger.info("3");
                //更新钱包和交易流水状态
                payService.afterPay(payModel);
                logger.info(payModel.toString());

                return "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg></return_msg>" + "</xml> ";
    }



    //@PostConstruct
    public void test(){
        try{
            ObjectMapper mapper =new ObjectMapper();
            String str = mapper.writeValueAsString(recharge(18,11));
            System.out.println(str);
        }catch (Exception e){

        }
    }


    private Map createPayOrder(PayModel payModel) {
        Map<String,String> data = new HashMap<String, String>();
        data.put("body", payModel.getBody());
        data.put("detail",payModel.getDetail());
        data.put("device_info", "");
        data.put("fee_type", "CNY");
        data.put("total_fee", payModel.getTotalFee());
        data.put("out_trade_no",WXPayUtil.generateNonceStr());
        data.put("spbill_create_ip", payModel.getIp());
        data.put("notify_url", WXConstants.notify_url);
        data.put("trade_type", "APP");  // 此处指定为扫码支付
        data.put("product_id", payModel.getStudentId());
        try{
            Map<String, String> resp = wxPay.unifiedOrder(data);
            return resp;
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return  null;
    }

}
