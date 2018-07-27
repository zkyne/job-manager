package com.zkyne.jobmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName: IndexController
 * @Description:
 * @Author: zkyne
 * @Date: 2018/4/11 15:31
 */
@Controller
@RequestMapping(value = "/")
public class IndexController {

    @RequestMapping(value = {"", "index"})
    public String index() {
        return "index/index";
    }

}
