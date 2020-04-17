package com.java.controller.intf;

import com.java.model.vo.ResultData;

public interface SystemController {
    // 登录
    ResultData login();
    // 退出登录
    ResultData logout();
    // 修改信息
    ResultData modifyInfo();
    // 注册
    ResultData register();

}
