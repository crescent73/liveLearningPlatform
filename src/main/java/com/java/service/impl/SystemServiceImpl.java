package com.java.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.constant.enums.UserRoleEnum;
import com.java.dao.UserDao;
import com.java.model.entity.User;
import com.java.service.intf.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemServiceImpl implements SystemService {
    @Autowired
    private UserDao userDao;

    /**
     * 登录
     * 检查条件：
     * name，password，userType
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        QueryWrapper<User> userSelect = new QueryWrapper<>();
        userSelect.eq("user_nickname",user.getUserNickname());
        userSelect.eq("user_password",user.getUserPassword());
        userSelect.eq("user_role", user.getUserRole());
        List<User> loginUser = userDao.selectList(userSelect);
        if (loginUser.size()>0){
            return loginUser.get(0);
        } else {
            return null;
        }
    }

    @Override
    public int logout() {
        return 0;
    }

    @Override
    public User modifyInfo() {
        return null;
    }

    @Override
    public User register() {
        return null;
    }
}
