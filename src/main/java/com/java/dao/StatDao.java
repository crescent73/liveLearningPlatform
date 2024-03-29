package com.java.dao;

import com.java.constant.consist.Constant;
import com.java.model.entity.Guest;
import com.java.model.entity.LiveUser;
import com.java.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class StatDao {

    @Autowired
    RedisUtil redisUtil;

    // 添加在线用户
    public boolean pushOnlineUser(LiveUser liveUser,Integer courseScheduleId){
        long result = redisUtil.sSet(Constant.ONLINE_USER_KEY+"-"+courseScheduleId, liveUser);
        return result>0?true:false;
    }

    // 删除在线用户
    public boolean popOnlineUser(LiveUser liveUser,Integer courseScheduleId){
        long result = redisUtil.setRemove(Constant.ONLINE_USER_KEY+"-"+courseScheduleId, liveUser);
        return result>0?true:false;
    }

    // 获取所有在线用户
    public Set getAllUserOnline(Integer courseScheduleId){
        return redisUtil.sGet(Constant.ONLINE_USER_KEY+"-"+courseScheduleId);
    }

    // 添加访客记录
    public boolean pushGuestHistory(Guest guest,Integer courseScheduleId){
        //最多存储指定个数的访客
        if (redisUtil.lGetListSize(Constant.GUEST_KEY+"-"+courseScheduleId) == 2000l){
            if(!redisUtil.lPop(Constant.GUEST_KEY+"-"+courseScheduleId))
                return false;
        }
        return redisUtil.lSet(Constant.GUEST_KEY+"-"+courseScheduleId, guest);
    }

    // 获取所有的访客记录
    public List getGuestHistory(Integer courseScheduleId){
        return redisUtil.lGet(Constant.GUEST_KEY+"-"+courseScheduleId,0,-1);
    }
}
