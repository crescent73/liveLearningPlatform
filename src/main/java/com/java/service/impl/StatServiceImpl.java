package com.java.service.impl;

import com.java.dao.StatDao;
import com.java.model.entity.Guest;
import com.java.model.entity.LiveUser;
import com.java.service.intf.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class StatServiceImpl implements StatService {
    @Autowired
    private StatDao  statDao;
    public void pushOnlineUser(LiveUser liveUser){
        statDao.pushOnlineUser(liveUser);
    }

    public void popOnlineUser(LiveUser liveUser){
       statDao.popOnlineUser(liveUser);
    }
    public List getAllUserOnline(){
        Set allUserOnlineSet = statDao.getAllUserOnline();
        return new ArrayList(allUserOnlineSet);
    }
    public void pushGuestHistory(Guest guest){
        statDao.pushGuestHistory(guest);
    }
    public List getGuestHistory(){
        return statDao.getGuestHistory();
    }
}
