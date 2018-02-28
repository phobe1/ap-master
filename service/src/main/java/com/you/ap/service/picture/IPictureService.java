package com.you.ap.service.picture;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IPictureService {

    public static String bucketName="wooyo";

    //文件存储目录
    public static String filedir = "data/";

    public void uploadImg2Oss(String url)  throws Exception;

    public String uploadImg2Oss(MultipartFile file);


    public String getUrl(String key);


    public void readObject(String key) throws IOException;

    public void listObject();


}
