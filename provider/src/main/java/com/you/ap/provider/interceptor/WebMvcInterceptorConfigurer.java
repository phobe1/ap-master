package com.you.ap.provider.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author gaoxiaoning created on 17/12/13.
 * @version
 */
@Configuration
public class WebMvcInterceptorConfigurer extends WebMvcConfigurerAdapter {
    @Autowired private AccessInterceptor accessInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessInterceptor).
                excludePathPatterns("/teachcourse/common/allcourses.json","/**/login/**","/**/register/**","/**/alipay/**","/**/weixinpay/**");
        super.addInterceptors(registry);
    }

}
