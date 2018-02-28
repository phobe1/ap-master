package com.you.ap.common.constant;

public class WXConstants {

    public static final String appid = "wx1cc921938ad2e075";//在微信开发平台登记的app应用
    public static final String appsecret = "da260fde610ad0eacb2985abde3bdad7";
    public static final String partner = "1492823622";//商户号
    public static final String partnerkey ="FENGSHUOCUIXIAOLONGwangpeng19891";//不是商户登录密码，是商户在微信平台设置的32位长度的api秘钥，
    public static final String createOrderURL="https://api.mch.weixin.qq.com/pay/unifiedorder";
    public static final String backUri="http://XXXXXXXX/api/weixin/topay.jhtml";//微信支付下单地址
    public static final String notify_url="http://47.104.8.224:8900/ap/newpay/wx/weixinpay/notify_url";//异步通知地址
}
