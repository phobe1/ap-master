package com.you.ap.common.config;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.you.ap.common.constant.WXConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class WXPayConfiguration {
    @Bean
    public WXPay createWXPayClient(@Autowired WXPayConfig wxPayConfig){
        try {
            return new WXPay(wxPayConfig);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }

    @Bean
    public WXPayConfig createWXConfig(){
        return new WXPayConfig() {
            @Override
            public String getAppID() {
                return WXConstants.appid;
            }

            @Override
            public String getMchID() {
                return WXConstants.partner;
            }

            @Override
            public String getKey() {
                return WXConstants.partnerkey;
            }

            @Override
            public InputStream getCertStream() {
                return null;
            }

            @Override
            public int getHttpConnectTimeoutMs() {
                return 8000;
            }

            @Override
            public int getHttpReadTimeoutMs() {
                return 10000;
            }
        };
    }

}
