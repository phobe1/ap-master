package com.you.ap.provider.controller.pay;


import com.alipay.api.*;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.you.ap.domain.enums.user.UserTypeEnum;
import com.you.ap.domain.schema.order.TradeDO;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.provider.config.AlipayConfig;
import com.you.ap.service.wallet.ITradeService;
import com.you.ap.service.wallet.IWalletService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;


@Controller
public class AlipayController {

    @Autowired
    private ITradeService tradeService;

    @Autowired
    private IWalletService walletService;

    private org.slf4j.Logger logger = LoggerFactory.getLogger(AlipayController.class);


    @RequestMapping(value="/pay/createOrder",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public ApiResponse alipay(
            @RequestAttribute("studentId")int studentId,
            @RequestParam("teacherId")int teacherId,
            @RequestParam("note")String note,
            @RequestParam("tradeMoney")double tradeMoney) throws AlipayApiException{

        String orderStr = "";
        try {

            /****** 1.封装你的交易订单开始 *****/                                        //自己用

            TradeDO tradeDO=new TradeDO();
            tradeDO.setStudentId(studentId);
            if(teacherId!=0) {tradeDO.setTeacherId(teacherId);}
            tradeDO.setOrderNumber(UUID.randomUUID().toString().replace("-",""));
            tradeDO.setMoney(tradeMoney);
            tradeDO.setNote(note);
            tradeDO.setStatus(0);
            logger.info(tradeDO.toString());



            /****** 1.封装你的交易订单结束 *****/

            Map<String,String> orderMap = new LinkedHashMap<String,String>();            //订单实体
            Map<String,String> bizModel = new LinkedHashMap<String,String>();            //公共实体

            /****** 2.商品参数封装开始 *****/                                            //手机端用


            // 商户订单号，商户网站订单系统中唯一订单号，必填
            orderMap.put("out_trade_no",tradeDO.getOrderNumber());
            // 学生用户id／教师id
            orderMap.put("body",studentId+"#"+teacherId);
            // 订单名称，必填
            orderMap.put("subject",note);
            // 付款金额，必填
            orderMap.put("total_amount",tradeMoney+"");




            // 超时时间 可空
            orderMap.put("timeout_express","30m");
            // 销售产品码 必填
            orderMap.put("product_code","QUICK_WAP_PAY");

            /****** 2.商品参数封装结束 *****/

            /******--------------- 3.公共参数封装 开始 ------------------------*****/        //支付宝用
            //1.商户appid
            bizModel.put("app_id", AlipayConfig.APPID);
            //2.请求网关地址
            bizModel.put("method",AlipayConfig.URL);
            //3.请求格式
            bizModel.put("format",AlipayConfig.FORMAT);
            //4.回调地址
            bizModel.put("return_url",AlipayConfig.return_url);
            //5.私钥
            bizModel.put("private_key",AlipayConfig.RSA_PRIVATE_KEY);
            //6.商家id
            bizModel.put("seller_id","2088921037477723");
            //7.加密格式
            bizModel.put("sign_type",AlipayConfig.SIGNTYPE+"");

            /******--------------- 3.公共参数封装 结束 ------------------------*****/

            //实例化客户端
            AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY,AlipayConfig.SIGNTYPE);

            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest ali_request = new AlipayTradeAppPayRequest();

            //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            model.setPassbackParams("支付宝支付");;  //描述信息  添加附加数据
            model.setBody(orderMap.get("body"));                        //商品信息
            model.setSubject(orderMap.get("subject"));                  //商品名称
            model.setOutTradeNo(orderMap.get("out_trade_no"));          //商户订单号(自动生成)
            model.setTimeoutExpress(orderMap.get("timeout_express"));     //交易超时时间
            model.setTotalAmount(orderMap.get("total_amount"));         //支付金额
            model.setProductCode(orderMap.get("product_code"));         //销售产品码
            model.setSellerId("2088921037477723");                        //商家id
            ali_request.setBizModel(model);
            ali_request.setNotifyUrl(AlipayConfig.notify_url);          //回调地址

            AlipayTradeAppPayResponse response = client.sdkExecute(ali_request);
            orderStr = response.getBody();

            tradeService.addTradeOrder(tradeDO);

            return ApiResponse.buildSuccess(orderStr,"订单生成成功");

        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.buildFailure("订单生成失败");

        }


    }

    /**
     * 支付宝支付成功后.回调该接口
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value="/alipay/notify_url",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String notify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String> params = new HashMap<String, String>();


        //1.从支付宝回调的request域中取值
        Map<String, String[]> requestParams = request.getParameterMap();

        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }

        System.out.println("--------------支付宝回调参数---------------");
        for(Map.Entry<String,String> p:params.entrySet()){
            System.out.println(p.getKey()+":"+p.getValue());
        }
        System.out.println("------------------------------------------");


        //2.封装必须参数
        String out_trade_no = request.getParameter("out_trade_no");            // 商户订单号
        //String orderType = request.getParameter("body");                    // 订单内容
        String tradeStatus = request.getParameter("trade_status");            //交易状态

        //3.签名验证(对支付宝返回的数据验证，确定是支付宝返回的)
        boolean signVerified = false;
        try {
            //3.1调用SDK验证签名
            signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGNTYPE);

        //4.对验签进行处理
        if (signVerified) {    //验签通过
            if(tradeStatus.equals("TRADE_SUCCESS")) {    //只处理支付成功的订单: 修改交易表状态,支付成功
                TradeDO tradeDO = tradeService.selectByOrderNumber(out_trade_no);
                tradeDO.setStatus(3);            //支付完成
                int returnResult = tradeService.updateByPrimaryKeySelective(tradeDO);    //更新交易表中状态

                String subject=params.get("subject");
                logger.info("subject:"+subject);
                double total_amount=Double.parseDouble(params.get("total_amount"));
                logger.info("total_amount:"+total_amount);
                String[] Ids=params.get("body").split("#");
                int studentId=Integer.parseInt(Ids[0]);
                int teacherId=Integer.parseInt(Ids[1]);

                if(subject.equals("支付宝充值wooyo币")){
                        walletService.addCoin(studentId, UserTypeEnum.Student.getKey(),(int)(total_amount*10) );
                        logger.info("支付宝充值wooyo币成功");
                }
                else if(subject.equals("支付宝约课费用")){
                        walletService.addMoney(teacherId,UserTypeEnum.Teacher.getKey(),total_amount);
                        logger.info("支付宝约课费用支付成功");
                }
                else{
                    logger.error("商户系统钱包更新失败");
                    return "fail";
                }


                if(returnResult>0){
                    return "success";
                }else{
                    logger.error("订单状态异常");
                    return "fail";
                }
            }else{
                logger.error("支付宝侧支付失败");
                return "fail";
            }
        } else {  //验签不通过
            logger.error("支付宝验签失败");
            return "fail";
        }

        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }
//
//
//    /**
//     * 支付宝支付成功后.通知页面
//     *@author Zhao
//     *@date 2017年11月2日
//     *@param request
//     *@return
//     *@throws UnsupportedEncodingException
//     */
//    @RequestMapping(value="/alipay/return_url",method={RequestMethod.POST,RequestMethod.GET})
//    @ResponseBody
//    public Model returnUrl(@RequestParam("id") String id,HttpServletRequest request,Model model) throws UnsupportedEncodingException {
//        System.err.println("。。。。。。 同步通知 。。。。。。");
//        System.err.println("。。。。。。 同步通知 。。。。。。");
//        System.err.println("。。。。。。 同步通知 。。。。。。");
//        Map  returnMap = new HashMap();
//        try {
//
//            TradeDO tradeDO = tradeService.selectByOrderNumber(id);
//            // 返回值Map
//            if(tradeDO !=null && tradeDO.getTradeStatus() == 2){
//                User user = userService.selectByPrimaryKey(trade.gettUserId());
//                returnMap.put("tradeType", trade.getTradeType());             //支付方式
//                returnMap.put("phoneNum", user.getPhoneNumber());             //支付帐号
//                returnMap.put("tradeMoney", trade.getTradeMoney()+"");        //订单金额
//
//            }else{
//                model.addAttribute("msg", "查询失败");
//                model.addAttribute("status", 0);
//            }
//            model.addAttribute("returnMap", returnMap);
//            System.err.println(returnMap);
//            model.addAttribute("msg", "查询成功");
//            model.addAttribute("status", 0);
//        } catch (Exception e) {
//            model.addAttribute("msg", "查询失败");
//            model.addAttribute("status", 1);
//        }
//
//        return model;
//    }
}
