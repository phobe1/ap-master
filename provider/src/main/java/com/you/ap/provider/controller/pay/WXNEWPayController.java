package com.you.ap.provider.controller.pay;

import com.alibaba.fastjson.JSONObject;
import com.you.ap.common.constant.WXConstants;
import com.you.ap.common.helper.GetWxOrderno;
import com.you.ap.common.helper.RequestHandler;
import com.you.ap.common.helper.Sha1Util;
import com.you.ap.common.helper.TenpayUtil;
import com.you.ap.domain.schema.order.TradeDO;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.service.pay.IWXPayService;
import com.you.ap.service.wallet.ITradeService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

@Controller
@RequestMapping("/newpay/wx")
public class WXNEWPayController {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(WXNEWPayController.class);
    private static String appid = WXConstants.appid;
    private static String appsecret = WXConstants.appsecret;
    private static String partner = WXConstants.partner;
    private static String partnerkey = WXConstants.partnerkey;

    @Autowired
    private ITradeService tradeService;
    @Autowired
    private IWXPayService iwxPayService;

    @RequestMapping(value = "/topay",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public ApiResponse topay(
            @RequestAttribute("studentId")int studentId,
            @RequestParam("teacherId")int teacherId,
            @RequestParam("note")String note,
            @RequestParam("tradeMoney")double tradeMoney,
            HttpServletRequest request,HttpServletResponse response) throws Exception{
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        //ryan add
        TradeDO tradeDO=new TradeDO();
        tradeDO.setStudentId(studentId);
        tradeDO.setOrderNumber(UUID.randomUUID().toString().replace("-",""));
        tradeDO.setMoney(tradeMoney);
        tradeDO.setNote(note);
        tradeDO.setStatus(0);
        logger.info(tradeDO.toString());
        //end
//        PrintWriter out = response.getWriter();
        String json=null;
        JSONObject retMsgJson=new JSONObject();
        //金额转化为分为单位
        double sessionmoney = tradeMoney;
        String finalmoney = String.format("%.2f", sessionmoney);
        finalmoney = finalmoney.replace(".", "");
        String currTime = TenpayUtil.getCurrTime();
        //8位日期
        String strTime = currTime.substring(8, currTime.length());
        //四位随机数
        String strRandom = TenpayUtil.buildRandom(4) + "";
        //10位序列号,可以自行调整。
        String strReq = strTime + strRandom;
        //商户号
        String mch_id = partner;
        //子商户号  非必输
        //String sub_mch_id="";
        //设备号   非必输
        String device_info="";
        //随机数
        String nonce_str = strReq;
        String body = tradeDO.getStudentId()+"#"+tradeDO.getTeacherId();
        //附加数据
        String attach = studentId+"#"+teacherId;
        //商户订单号
//        String out_trade_no = order.getSn()+"|"+System.currentTimeMillis();//订单编号加时间戳
        int intMoney = Integer.parseInt(finalmoney);
        //总金额以分为单位，不带小数点
        String total_fee = String.valueOf(intMoney);
        //订单生成的机器 IP
        String spbill_create_ip = request.getRemoteAddr();
        String notify_url =WXConstants.notify_url;//微信异步通知地址
        String trade_type = "APP";//app支付必须填写为APP
        //对以下字段进行签名
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", appid);
        packageParams.put("attach", attach);
        packageParams.put("body", body);
        packageParams.put("mch_id", mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("notify_url", notify_url);
        packageParams.put("out_trade_no", tradeDO.getOrderNumber());
        packageParams.put("spbill_create_ip", spbill_create_ip);
        packageParams.put("total_fee", total_fee);
        packageParams.put("trade_type", trade_type);
        RequestHandler reqHandler = new RequestHandler(request, response);
        reqHandler.init(appid, appsecret, partnerkey);
        String sign = reqHandler.createSign(packageParams);//获取签名
        String xml="<xml>"+
                "<appid>"+appid+"</appid>"+
                "<attach>"+attach+"</attach>"+
                "<body><![CDATA["+body+"]]></body>"+
                "<mch_id>"+mch_id+"</mch_id>"+
                "<nonce_str>"+nonce_str+"</nonce_str>"+
                "<notify_url>"+notify_url+"</notify_url>"+
                "<out_trade_no>"+tradeDO.getOrderNumber()+"</out_trade_no>"+
                "<spbill_create_ip>"+spbill_create_ip+"</spbill_create_ip>"+
                "<total_fee>"+total_fee+"</total_fee>"+
                "<trade_type>"+trade_type+"</trade_type>"+
                "<sign>"+sign+"</sign>"+
                "</xml>";
        String allParameters = "";
        logger.info(sign);
        logger.info(xml);

        try {
            allParameters =  reqHandler.genPackage(packageParams);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String createOrderURL = WXConstants.createOrderURL;
        String prepay_id="";
        try {
            prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);
            if(prepay_id.equals("")){
                retMsgJson.put("msg", "error");
                json=retMsgJson.toString();
                return ApiResponse.buildFailure(json,"error");
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        //获取到prepayid后对以下字段进行签名最终发送给app
        SortedMap<String, String> finalpackage = new TreeMap<String, String>();
        String timestamp = Sha1Util.getTimeStamp();
        finalpackage.put("appid", appid);
        finalpackage.put("timestamp", timestamp);
        finalpackage.put("noncestr", nonce_str);
        finalpackage.put("partnerid", partner);
        finalpackage.put("package", "Sign=WXPay");
        finalpackage.put("prepayid", prepay_id);
        String finalsign = reqHandler.createSign(finalpackage);
        retMsgJson.put("msg", "ok");
        retMsgJson.put("appid", appid);
        retMsgJson.put("timestamp", timestamp);
        retMsgJson.put("noncestr", nonce_str);
        retMsgJson.put("partnerid", partner);
        retMsgJson.put("prepayid", prepay_id);
        retMsgJson.put("package", "Sign=WXPay");
        retMsgJson.put("sign", finalsign);
        json=retMsgJson.toString();
        logger.info(json);
        json.toString().replace("\\","");

        tradeService.addTradeOrder(tradeDO);

        return ApiResponse.buildSuccess(json.toString().replace("\\",""),"successful");
    }

    @RequestMapping(value="/weixinpay/notify_url",method = RequestMethod.POST)
    @ResponseBody
    public void notify(
            HttpServletRequest request,HttpServletResponse response
    ){

        logger.info("微信支付回调接口");
        try{
            InputStream is = request.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line="";
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            logger.info(sb.toString());

            response.getWriter().write(iwxPayService.checkPayStatus(sb.toString()));
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
