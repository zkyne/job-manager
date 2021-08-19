package com.zkyne.jobmanager.config;

import com.zkyne.jobmanager.interceptor.SecurityInterceptor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: WebAppConfig
 * @Description:
 * @Author: zkyne
 * @Date: 2018/7/18 9:48
 */
@SpringBootConfiguration
public class WebAppConfig implements WebMvcConfigurer {

    @Resource
    private SecurityInterceptor securityInterceptor;

    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册自定义拦截器，添加拦截路径和排除拦截路径
        registry.addInterceptor(securityInterceptor).addPathPatterns("/**").excludePathPatterns("/login","/login/login");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new GsonHttpMessageConverter());
    }
}
