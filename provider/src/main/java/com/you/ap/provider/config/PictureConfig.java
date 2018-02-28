package com.you.ap.provider.config;

import com.aliyun.oss.OSSClient;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PictureConfig {


    static Logger logger = Logger.getLogger(PictureConfig.class);

    private static String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    private static String accessKeyId = "LTAIScea7DaPjy6h";
    private static String accessKeySecret = "qxmrdbydEfROaNazv5q7fHwjjo9Qzo";
    private static String bucketName = "wooyo";

    @Bean
    public OSSClient oSSClientFatory(){
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        return ossClient;
    }



}
