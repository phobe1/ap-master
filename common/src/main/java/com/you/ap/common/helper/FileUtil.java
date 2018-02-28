package com.you.ap.common.helper;

import com.you.ap.common.constant.Constant;
import com.you.ap.domain.enums.user.UserTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class FileUtil {

    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static String saveFile(MultipartFile file, UserTypeEnum userTypeEnum,int id, int type){
        try{
            if (file== null || file.getInputStream()== null){
                return null;
            }
            InputStream in = file.getInputStream();
            String path= Constant.FILE_PATH+"_"+id+ "_"+type+"_"+userTypeEnum.key+"_"+System.currentTimeMillis();
            File file1 = new File(path);
            if(file1.exists()){
                file1.delete();
            }
            OutputStream out= new FileOutputStream(path);
            byte [] buffer = new byte[1024];
            while (in.read(buffer)>0){
                out.write(buffer);
            }
            out.close();
            in.close();
            return  path;
        }catch (Exception e){
            logger.error("saveFile",e);
            return  null;
        }
    }
}
