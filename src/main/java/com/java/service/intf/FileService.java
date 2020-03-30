package com.java.service.intf;

import com.java.model.entity.File;

import java.util.List;

public interface FileService {
    // 上传文档
    int uploadFile();
    // 获取文档列表
    List<File> getFileList();
    // 下载文档
    File downloadFile();
    // 修改文档信息
    int modifyFileInfo();
    // 删除文档
    int deleteFile();
}
