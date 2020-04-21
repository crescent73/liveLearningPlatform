package com.java.service.impl;

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

    @Override
    public User login(User user) {
        List<User> loginUser = userDao.select(user);
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
