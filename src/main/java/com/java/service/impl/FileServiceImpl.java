package com.java.service.impl;

import com.java.model.entity.File;
import com.java.service.intf.FileService;

import java.util.List;

public class FileServiceImpl implements FileService {
    @Override
    public int uploadFile(File file) {
        return 0;
    }

    @Override
    public List<File> getFileList(File file) {
        return null;
    }

    @Override
    public File downloadFile(File file) {
        return null;
    }

    @Override
    public int modifyFileInfo(File file) {
        return 0;
    }

    @Override
    public int deleteFile(File file) {
        return 0;
    }
}
