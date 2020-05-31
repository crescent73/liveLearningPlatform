package com.java.service.impl;

import com.java.dao.LiveUserDao;
import com.java.model.entity.LiveUser;
import com.java.service.intf.LiveUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LiveUserServiceImpl implements LiveUserService {
    @Autowired
    private LiveUserDao liveUserDao;

    @Override
    public LiveUser findOne(String ip) {
        return liveUserDao.findOne(ip);
    }

    @Override
    public int insert(LiveUser liveUser) {
        return liveUserDao.insert(liveUser);
    }
}
