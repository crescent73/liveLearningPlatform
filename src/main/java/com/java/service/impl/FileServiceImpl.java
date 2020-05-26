package com.java.service.impl;

import com.java.constant.enums.ResultCodeEnum;
import com.java.dao.FileDao;
import com.java.model.entity.File;
import com.java.service.intf.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileDao fileDao;

    @Override
    public int uploadFile(File file,List<MultipartFile> files) {
        for (MultipartFile multipartFile : files) {
            java.io.File filePath = new java.io.File("filePath");
            if(!filePath.exists()) {
                filePath.mkdirs();
            }
            String newFileName = file.getFileId() + "_" + UUID.randomUUID()+ "_" + "originalFileName";
            try {
                multipartFile.transferTo(new java.io.File("newPath"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 将数据添加到数据库中
            int insert = fileDao.insert(file);
        }
        return 0;
    }

    @Override
    public List<File> getFileList(File file) {
        return null;
    }

    @Override
    public File downloadFile(File file) {
        java.io.File downloadFile = new java.io.File("path");
        return null;
    }

    @Override
    public int modifyFileInfo(File file) {
        return 0;
    }

    @Override
    public int deleteFile(File file) {
        java.io.File deleteFile = new java.io.File("path");
        if (deleteFile.exists() && deleteFile.isFile()) {
            if (deleteFile.delete()) {
                //删除表中字段
//                int result = attachmentDao.delete(attachment);
            }
        }
        return 0;
    }
}
