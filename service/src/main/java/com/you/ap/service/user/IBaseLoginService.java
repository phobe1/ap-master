package com.you.ap.service.user;

import com.you.ap.domain.form.user.UserLoginForm;
import com.you.ap.domain.vo.ApiResponse;

public interface IBaseLoginService {

    ApiResponse register(UserLoginForm userLoginForm);

    ApiResponse login(UserLoginForm userLoginForm);
}
