package com.you.ap.service.user.imp;

import com.you.ap.common.constant.Constant;
import com.you.ap.common.helper.CommonUtil;
import com.you.ap.common.helper.FileUtil;
import com.you.ap.domain.enums.user.UserTypeEnum;
import com.you.ap.domain.model.TokenModel;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.service.user.IPhotoUploadService;
import com.you.ap.service.user.IStudentInfoService;
import com.you.ap.service.user.ITeacherInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
@Service
public class PhotoUploadServiceImp implements IPhotoUploadService {

    private Logger logger = LoggerFactory.getLogger(PhotoUploadServiceImp.class);

    @Autowired  private IStudentInfoService studentInfoService;
    @Autowired private ITeacherInfoService teacherInfoService;


    @Override
    public ApiResponse savePhoto(String token, MultipartFile photo,boolean isphoto) {
       try{
           TokenModel tokenModel = CommonUtil.getTokenModelFromToken(token);
           if (tokenModel == null ){
               return ApiResponse.buildFailure("token is valid");
           }
           String photoPath = "";
           if(isphoto){
               photoPath=FileUtil.saveFile(photo,tokenModel.getUserType(),tokenModel.getUserId(),1);
           }else{
               photoPath=FileUtil.saveFile(photo,tokenModel.getUserType(),tokenModel.getUserId(),2);
           }
           if (photoPath == null || token == null){
               return ApiResponse.buildFailure("token or photo is valid");
           }
           if(!isphoto&&tokenModel.getUserType() == UserTypeEnum.Student){
               logger.info("branch1 "+token+" "+photoPath);
               return studentInfoService.updateIdentifyPhoto(tokenModel.getUserId(),photoPath);
           }
           if (tokenModel.getUserType() == UserTypeEnum.Student){
               logger.info("branch2 "+token+" "+photoPath);
               return studentInfoService.updatePhoto(tokenModel.getUserId(),photoPath);
           }else{
               logger.info("branch3 "+token+" "+photoPath);
               return teacherInfoService.updatePhoto(tokenModel.getUserId(),photoPath);
           }
       }catch (Exception e){
           logger.error("savePhoto",e);
           return ApiResponse.buildFailure(e.getMessage() ) ;
       }
    }



}
