package com.you.ap.provider.controller;


import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.service.picture.IPictureService;
import com.you.ap.service.user.IPhotoUploadService;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/photo")
public class PhotoController {

    @Autowired private IPhotoUploadService photoUploadService;

    @Autowired
    private IPictureService pictureService;


    private Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/student/upload", method = RequestMethod.POST)
	@ResponseBody
    public Object uploadStudentPhoto(
            @RequestParam("token") String token,
            @RequestParam(value = "photo") MultipartFile photo) {
        logger.info("token is "+token +"  ");
        return photoUploadService.savePhoto(token,photo,true);
    }


    @RequestMapping(value = "/student/indentify/upload", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadStudentIndentify(
            @RequestParam("token") String token,
            @RequestParam(value = "photo") MultipartFile photo) {

        return photoUploadService.savePhoto(token,photo,false);
    }

    @RequestMapping(value = "/teacher/upload", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadTeacherPhoto(
            @RequestParam("token") String token,
            @RequestParam(value = "photo") MultipartFile photo) {
        return photoUploadService.savePhoto(token,photo,true);
    }

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public void uploadStudentPhoto(
            @RequestParam("path") String path,
            HttpServletResponse response) {
        try{
            response.setContentType("image/jpeg;charset=utf-8");
            InputStream in = new FileInputStream(path);
            byte[] buff = new byte[1024];
            while(in.read(buff)>0){
                response.getOutputStream().write(buff);
            }
            in.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public Object updateByAliyu(@RequestParam("token") String token,@RequestParam(value = "photo") MultipartFile photo){
        String outcome= pictureService.uploadImg2Oss(photo);
        System.out.println(outcome);
        return outcome;
    }

    @RequestMapping(value = "/login/video/{key}", method = RequestMethod.GET)
    @ResponseBody
    public void video(@PathVariable("key") String key) throws IOException {
        pictureService.readObject(key);
    }

    @RequestMapping(value = "/login/list", method = RequestMethod.GET)
    @ResponseBody
    public void video() throws IOException {
        pictureService.listObject();
    }

    @RequestMapping(value = "/login/videourl/{key}", method = RequestMethod.GET)
    @ResponseBody
    public Object getideo(@PathVariable("key") String key) throws IOException {
        return  pictureService.getUrl(key);
    }

}
