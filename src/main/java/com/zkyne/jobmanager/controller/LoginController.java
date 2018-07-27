package com.zkyne.jobmanager.controller;

import com.zkyne.jobmanager.aspect.LogHandle;
import com.zkyne.jobmanager.common.constant.Constants;
import com.zkyne.jobmanager.po.SysUser;
import com.zkyne.jobmanager.service.ISysUserService;
import com.zkyne.jobmanager.common.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ClassName: LoginController
 * @Description:
 * @Author: zkyne
 * @Date: 2018/4/13 14:34
 */
@Controller
@RequestMapping("login")
public class LoginController {

    @Resource
    private ISysUserService sysUserService;

    @RequestMapping("")
    public String toLogin(){
        return "index/login";
    }

    @RequestMapping(value = "/login", produces = "application/json;charset=utf-8")
    @ResponseBody
    @LogHandle
    public Map<String,Object> login(@RequestParam("userName")String userName, @RequestParam("password") String password, HttpServletRequest request){
        //如果用户已经登录，那么直接到主页面
        long currentUserId = AuthUtil.getCurrentUserId();
        if(currentUserId > 0){
            return ResultUtils.success();
        }
        if(StringUtils.isBlank(userName)){
            return ResultUtils.error("用户名为空");
        }
        if(StringUtils.isBlank(password)){
            return ResultUtils.error("密码为空");
        }
        SysUser sysUser = sysUserService.findByUserName(userName);
        if(sysUser == null){
            return ResultUtils.error("该用户不存在");
        }
        if(sysUser.getPassword().equals(EncryptUtils.md5(userName,password))){
            // 登录成功
            request.getSession().setAttribute(Constants.SYS_USER_INFO_STORED_IN_SESSION, sysUser);
            return ResultUtils.success();
        }else{
            return ResultUtils.error("用户名或密码错误");
        }
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute(Constants.SYS_USER_INFO_STORED_IN_SESSION);
        AuthUtil.clear();
        return "redirect:/login";
    }
}
