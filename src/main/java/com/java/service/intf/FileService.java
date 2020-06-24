package com.java.service.intf;

import com.java.model.entity.CourseFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface FileService {
    // 上传文档
    int uploadFile(CourseFile file, MultipartFile files);
    // 获取文档列表
    List<CourseFile> getFileList(CourseFile file);
    // 下载文档
    File downloadFile(Integer fileId);
    // 修改文档信息
    int modifyFileInfo(CourseFile file);
    // 删除文档
    int deleteFile(Integer fileId);
}
