package com.java.service.intf;

import com.java.model.entity.File;

import java.util.List;

public interface FileService {
    // 上传文档
    int uploadFile(File file);
    // 获取文档列表
    List<File> getFileList(File file);
    // 下载文档
    File downloadFile(File file);
    // 修改文档信息
    int modifyFileInfo(File file);
    // 删除文档
    int deleteFile(File file);
}
