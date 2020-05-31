package com.java.service.intf;

import com.java.model.entity.Notice;

import java.util.List;

public interface NoticeService {
    // 获取公告列表
    List<Notice> getNoticeList(Notice notice);
    // 发布公告
    int publishNotice(Notice notice);
    // 修改公告
    int modifyNotice(Notice notice);
    // 删除公告
    int deleteNotice(Notice notice);
}
