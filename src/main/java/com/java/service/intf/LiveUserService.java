package com.java.service.intf;

import com.java.model.entity.LiveUser;

public interface LiveUserService {
    LiveUser findOne(String ip);
    int insert(LiveUser liveUser);
}
