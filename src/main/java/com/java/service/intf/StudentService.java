package com.java.service.intf;

import com.java.model.entity.File;
import com.java.model.vo.ResultData;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentService {

    //提交作业（文件）
    public ResultData submitAssignment(File file, String dirPath, List <MultipartFile> attachments);

    //添加附件
//    public ResultData addAttachment(Attachment attachment, List <MultipartFile> attachments);

}
