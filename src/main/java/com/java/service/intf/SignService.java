package com.java.service.intf;

import com.java.model.entity.Sign;

import java.util.List;

public interface SignService {
    // 发布签到
    int publishSign();
    // 查看签到情况
    List<Sign> getSignDetail();
    // 签到
    int sign();
}
