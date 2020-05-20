package com.java.service.intf;

import com.java.model.entity.Guest;
import com.java.model.entity.LiveUser;

import java.util.List;

public interface StatService {
    void pushOnlineUser(LiveUser liveUser,Integer courseScheduleId);
    void popOnlineUser(LiveUser liveUser,Integer courseScheduleId);
    List getAllUserOnline(Integer courseScheduleId);
    void pushGuestHistory(Guest guest,Integer courseScheduleId);
    List getGuestHistory(Integer courseScheduleId);
}
