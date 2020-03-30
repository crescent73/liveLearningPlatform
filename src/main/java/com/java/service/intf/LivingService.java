package com.java.service.intf;

import com.java.model.vo.ResultData;

public interface LivingService {
    // 发送直播流
    int sendLivingStream();
    // 获取直播流
    int getLivingStream();
}
