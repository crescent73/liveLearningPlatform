package com.java.service.intf;

import com.java.model.entity.User;

public interface SystemService {
    // 登录
    User login(User user);
    // 退出
    int logout();
    // 修改信息
    User modifyInfo();
    // 注册
    User register();
}
