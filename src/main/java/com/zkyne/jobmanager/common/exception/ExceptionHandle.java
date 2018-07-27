package com.zkyne.jobmanager.common.exception;

import com.zkyne.jobmanager.common.util.RequestUtil;
import com.zkyne.jobmanager.common.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: ExceptionHandle
 * @Description: 统一异常处理类
 * @Author: zkyne
 * @Date: 2018/6/22 11:00
 */
@Slf4j
@ControllerAdvice
@Component
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object handle(HttpServletRequest request, Exception e){
        if(RequestUtil.isAjax(request)){
            // ajax请求
            if(e instanceof JobManagerException){
                JobManagerException croException = (JobManagerException) e;
                return ResultUtils.error(croException.getErrorCode(),croException.getMessage());
            }else{
                log.error("服务器系统异常:{}",e);
                return ResultUtils.error(-1,"系统异常");
            }
        }else{
            log.error("服务器系统异常:{}",e);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("error/500");
            return modelAndView;
        }
    }
}
