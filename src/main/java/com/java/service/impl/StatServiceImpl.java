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
    private StatDao statDao;
    public void pushOnlineUser(LiveUser liveUser,Integer courseScheduleId){
        if (courseScheduleId == null) {
            System.out.println("courseScheduleId is null!!! 111");
        }
        statDao.pushOnlineUser(liveUser,courseScheduleId);
    }

    public void popOnlineUser(LiveUser liveUser,Integer courseScheduleId){
        if (courseScheduleId == null) {
            System.out.println("courseScheduleId is null!!! 222");
        }
       statDao.popOnlineUser(liveUser,courseScheduleId);
    }
    public List getAllUserOnline(Integer courseScheduleId){
        if (courseScheduleId == null) {
            System.out.println("courseScheduleId is null!!! 333");
        }
        Set allUserOnlineSet = statDao.getAllUserOnline(courseScheduleId);
        return new ArrayList(allUserOnlineSet);
    }
    public void pushGuestHistory(Guest guest,Integer courseScheduleId){
        if (courseScheduleId == null) {
            System.out.println("courseScheduleId is null!!! 444");
        }
        statDao.pushGuestHistory(guest,courseScheduleId);
    }
    public List getGuestHistory(Integer courseScheduleId){
        if (courseScheduleId == null) {
            System.out.println("courseScheduleId is null!!! 555");
        }
        return statDao.getGuestHistory(courseScheduleId);
    }
}
