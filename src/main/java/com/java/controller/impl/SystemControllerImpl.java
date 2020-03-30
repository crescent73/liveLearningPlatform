package com.java.controller.impl;

import com.java.model.vo.ResultData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class SystemControllerImpl implements com.java.controller.intf.SystemController {

    @RequestMapping("/login")
    public ResultData login(){
        return null;
    }

    @RequestMapping("/logout")
    public ResultData logout(){
        return null;
    }

    @RequestMapping("/modifyInfo")
    public ResultData modifyInfo(){
        return null;
    }

    @RequestMapping("/register")
    public ResultData register(){
        return null;
    }
}
