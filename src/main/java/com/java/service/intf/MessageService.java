package com.java.service.intf;

import com.java.model.vo.ResultData;

import java.util.List;

public interface MessageService {
    // 获取消息列表
    int getMessageList();
    // 发送消息
    int sendMessage();
}
