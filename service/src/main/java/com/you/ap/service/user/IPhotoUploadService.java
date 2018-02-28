package com.you.ap.service.user;

import com.you.ap.domain.vo.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IPhotoUploadService {

    ApiResponse savePhoto(String token, MultipartFile photo,boolean isphoto);


}
