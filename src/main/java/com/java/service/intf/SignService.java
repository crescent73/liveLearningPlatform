package com.java.service.intf;

import com.java.model.dto.SignDetail;
import com.java.model.entity.Sign;
import com.java.model.entity.SignStudent;

import java.util.List;

public interface SignService {
    // 发布签到
    int publishSign(Sign sign);
    // 查看签到情况
    List<Sign> getSignList(Sign sign);
    // 查看签到情况
    List<SignDetail> getSignDetail(SignStudent signStudent);
    // 签到
    int sign(SignStudent signStudent);
}
