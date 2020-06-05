package com.java.service.intf;

import com.java.model.entity.*;
import com.java.model.vo.ResultData;

import javax.servlet.http.HttpSession;

public interface SystemService {
    // 注册
    int register(User user);
    // 登录
    User login(User user);
    // 退出
    int logout(Long id, String userType);
    // 修改信息
    int modifyInfo(User user);

}
