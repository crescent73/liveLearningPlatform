package com.java.service.intf;

import com.java.model.entity.Video;

public interface VideoService {
    // 查看录播
    Video getVideo();
    // 删除录播
    int deleteVideo();
}
