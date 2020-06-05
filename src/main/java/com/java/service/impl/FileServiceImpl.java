package com.java.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.dao.FileDao;
import com.java.model.entity.CourseFile;
import com.java.service.intf.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileDao fileDao;

    @Override
    public int uploadFile(CourseFile courseFile, MultipartFile multipartFile) {
        java.io.File filePath = new java.io.File(courseFile.getFilePath());
        if(!filePath.exists()) {
            filePath.mkdirs();
        }
        String newFileName = UUID.randomUUID()+ "_" + courseFile.getFileName();
        System.out.println(newFileName);
        try {
            System.out.println(courseFile.getFilePath());
            multipartFile.transferTo(new java.io.File(courseFile.getFilePath()+"\\"+newFileName));
            courseFile.setFilePath(courseFile.getFilePath()+"\\"+newFileName);
            System.out.println(courseFile.getFilePath());
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        // 将数据添加到数据库中
        int insert = fileDao.insert(courseFile);
        return insert;
    }

    @Override
    public List<CourseFile> getFileList(CourseFile file) {
        QueryWrapper<CourseFile> courseFileQueryWrapper = new QueryWrapper<>();
        courseFileQueryWrapper.like(file.getFileTitle()!=null,"file_title",file.getFileTitle());
        courseFileQueryWrapper.eq(file.getFileId()!=null,"file_id",file.getFileId());
        courseFileQueryWrapper.eq(file.getCourseId()!=null,"course_id",file.getCourseId());
        courseFileQueryWrapper.eq(file.getUserId()!=null,"user_id",file.getUserId());
        List<CourseFile> courseFiles = fileDao.selectList(courseFileQueryWrapper);
        courseFiles.forEach(x->x.setFilePath(null));
        if (courseFiles.size()>0) {
            return courseFiles;
        } else {
            return null;
        }
    }

    @Override
    public File downloadFile(CourseFile file) {
        CourseFile courseFile = fileDao.selectById(file.getFileId());
//        System.out.println(courseFile);
        File downloadFile = null;
        try {
            downloadFile = new File(courseFile.getFilePath());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return downloadFile;
    }

    @Override
    public int modifyFileInfo(CourseFile file) {
        int result = fileDao.updateById(file);
        return result;
    }

    @Override
    public int deleteFile(Integer fileId) {
        CourseFile courseFile = fileDao.selectById(fileId);
        try{
            java.io.File deleteFile = new java.io.File(courseFile.getFilePath());
            if (deleteFile.exists() && deleteFile.isFile()) {
                if (deleteFile.delete()) {
                    //删除表中字段
                    int result = fileDao.deleteById(fileId);
                    return  result;
                } else  {
                    return -2; // 文件删除失败
                }
            } else {
                return -1; // 文件不存在
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // 文件不存在
        }
    }

}
