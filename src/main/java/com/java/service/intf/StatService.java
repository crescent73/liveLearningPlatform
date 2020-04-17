package com.java.service.intf;

import com.java.model.entity.Guest;
import com.java.model.entity.LiveUser;

import java.util.List;

public interface StatService {
    void pushOnlineUser(LiveUser liveUser);
    void popOnlineUser(LiveUser liveUser);
    List getAllUserOnline();
    void pushGuestHistory(Guest guest);
    List getGuestHistory();
}
