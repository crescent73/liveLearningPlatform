package com.java.service.intf;

import com.java.model.entity.Attachment;
import com.java.model.entity.File;
import com.java.model.entity.Notice;
import com.java.model.entity.PageParam;
import com.java.model.vo.ResultData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TeacherService {

    //发布公告
    public ResultData addNotice(Notice notice);

    //删除公告
    public ResultData deleteNotice(Long id);

    //修改公告
    public ResultData modifyNotice(Notice notice);

    //上传课程文档
    public ResultData addFile(File file, String dirPath, List <MultipartFile> attachments);

    //添加附件
    public ResultData addAttachment(Attachment attachment, List <MultipartFile> attachments);

    //修改文档信息
    public ResultData modifyFileInfo(File file);

    //删除文档
    public ResultData deleteFile(Long id);

    //删除文档中的附件
    public ResultData deleteAttachment(Attachment attachment);

    //发布作业
    public ResultData publishAssignment(File file, String dirPath, List <MultipartFile> attachments);

    //查看作业列表
    public ResultData viewAssignment(File file, PageParam pageParam);

    //评价作业
    public ResultData gradeAssignment(File file);

    //删除作业
    public ResultData deleteAssignment(Long id);

    //删除作业中的附件
    public ResultData deleteAssignmentFile(Attachment attachment);
}
