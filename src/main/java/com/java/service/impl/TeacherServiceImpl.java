package com.java.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.java.constant.enums.ResultCodeEnum;
import com.java.dao.*;
import com.java.model.entity.*;
import com.java.model.vo.Data;
import com.java.model.vo.ResultData;
import com.java.service.intf.SystemService;
import com.java.service.intf.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class TeacherServiceImpl {
    private ResultData resultData;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private NoticeDao noticeDao;


    /*private TeacherDao teacherDao;
    @Autowired
    public void setTeacherDao(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    @Autowired
    private AttachmentDao attachmentDao;

    @Autowired
    private FileDao fileDao;

    @Transactional
    public ResultData addNotice(Notice notice) {
        resultData = new ResultData <Data>();
        if(notice != null) {
            //course外键校验
            if(!isCourseExist(notice.getCourseId())) {
                resultData.setResult(ResultCodeEnum.DB_ADD_FAILURE_COURSE_NOT_EXIST);//课程不存在
                return resultData;
            }
            //teacher外键校验
            if(!isTeacherExist(notice.getPublisherId())) {
                resultData.setResult(ResultCodeEnum.DB_ADD_FAILURE_TEACHER_NOT_EXIST);//教师不存在
                return resultData;
            }

            int result = noticeDao.insert(notice);
            if(result > 0) {
                resultData.setResult(ResultCodeEnum.DB_ADD_SUCCESS); //添加成功
            } else {
                resultData.setResult(ResultCodeEnum.DB_ADD_FAILURE); //添加失败
            }
        } else {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);  //必要参数为空
        }
        return resultData;
    }

    @Transactional
    public ResultData deleteNotice(Integer id) {
        resultData = new ResultData <Data>();
        if(id != null) {
            Notice notice = new Notice();
            notice.setId(id);
            int result = noticeDao.delete(notice);
            if(result > 0) {
                resultData.setResult(ResultCodeEnum.DB_DELETE_SUCCESS);
            } else {
                resultData.setResult(ResultCodeEnum.DB_DELETE_FAILURE);
            }
        } else {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);  //必要参数为空
        }
        return resultData;
    }

    @Transactional
    public ResultData modifyNotice(Notice notice) {
        resultData = new ResultData <Data>();
        if(notice != null && notice.getId() > 0) { //course不为空且id存在
            //公告校验
            if(!isNoticeExist(notice.getId())) {
                resultData.setResult(ResultCodeEnum.DB_UPDATE_FAILURE_NOTICE_NOT_EXIST);//公告不存在
                return resultData;
            }
            //course外键校验
            if(!isCourseExist(notice.getCourseId())) {
                resultData.setResult(ResultCodeEnum.DB_UPDATE_FAILURE_COURSE_NOT_EXIST);//课程不存在
                return resultData;
            }
            //teacher外键校验
            if(!isTeacherExist(notice.getPublisherId())) {
                resultData.setResult(ResultCodeEnum.DB_UPDATE_FAILURE_TEACHER_NOT_EXIST);//教师不存在
                return resultData;
            }

            int result = noticeDao.update(notice);
            if(result > 0) {
                resultData.setResult(ResultCodeEnum.DB_UPDATE_SUCCESS); //修改成功
            } else {
                resultData.setResult(ResultCodeEnum.DB_UPDATE_ERROR);  //修改失败
            }
        } else {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL); //id直接返回数据必要参数为空
        }
        return resultData;
    }

    @Transactional
    public ResultData addFile(File file, String dirPath, List<MultipartFile> attachments) {
        resultData = new ResultData <Data>();
        boolean isSuccess = false;
        // 处理file
        if(file != null) {
            //course外键校验
            if (!isCourseExist(file.getCourseId())) {
                resultData.setResult(ResultCodeEnum.DB_ADD_FAILURE_COURSE_NOT_EXIST);//课程不存在
                return resultData;
            }
            //teacher外键校验
            if (!isTeacherExist(file.getUploaderId())) {
                resultData.setResult(ResultCodeEnum.DB_ADD_FAILURE_TEACHER_NOT_EXIST);//教师不存在
                return resultData;
            }
            int result = fileDao.insert(file);
            if(result > 0) {
                isSuccess = true;
            }
        } else {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);  //必要参数为空
            return resultData;
        }
        Attachment attachment = new Attachment();
        attachment.setFileId(file.getId());
        attachment.setAttachmentPath(dirPath);
        //调用添加附件！
        resultData = addAttachment(attachment,attachments);
        if(isSuccess && resultData.getCode().equals("406")){
            resultData.setResult(ResultCodeEnum.FILE_UPLOAD_SUCCESS);//文件上传成功
        } else {
            resultData.setResult(ResultCodeEnum.FILE_UPLOAD_FAILURE);//文件上传失败
        }
        return resultData;
    }

    @Transactional
    public ResultData publishAssignment(File file, String dirPath, List<MultipartFile> attachments) {
        resultData = new ResultData <Data>();
        boolean isSuccess = false;
        // 处理file
        if(file != null) {
            //course外键校验
            if (!isCourseExist(file.getCourseId())) {
                resultData.setResult(ResultCodeEnum.DB_ADD_FAILURE_COURSE_NOT_EXIST);//课程不存在
                return resultData;
            }
            //teacher外键校验
            if (!isTeacherExist(file.getUploaderId())) {
                resultData.setResult(ResultCodeEnum.DB_ADD_FAILURE_TEACHER_NOT_EXIST);//教师不存在
                return resultData;
            }
            int result = fileDao.insert(file);
            if(result > 0) {
                isSuccess = true;
            }
        } else {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);  //必要参数为空
            return resultData;
        }
        Attachment attachment = new Attachment();
        attachment.setFileId(file.getId());
        attachment.setAttachmentPath(dirPath);
        //调用添加附件！
        resultData = addAttachment(attachment,attachments);
        if(isSuccess && resultData.getCode().equals("406")){
            resultData.setResult(ResultCodeEnum.FILE_UPLOAD_SUCCESS);//文件上传成功
        } else {
            resultData.setResult(ResultCodeEnum.FILE_UPLOAD_FAILURE);//文件上传失败
        }
        return resultData;
    }

    @Transactional
    public ResultData addAttachment(Attachment myAttachment, List<MultipartFile> attachments) {
        resultData = new ResultData <Data>();
        // 处理attachment
        if(attachments !=null && !attachments.isEmpty() && attachments.size() > 0){
            for(MultipartFile attachment : attachments){
                String originalFileName = attachment.getOriginalFilename();
                System.out.println("dirPath: " + myAttachment.getAttachmentPath());
                java.io.File filePath = new java.io.File(myAttachment.getAttachmentPath());
                if(!filePath.exists()) {
                    filePath.mkdirs();
                }
                //使用UUID给附件重新命名（附件名+文件id+uuid+）
                String newFileName = myAttachment.getFileId() + "_" + UUID.randomUUID()+ "_" + originalFileName;
                System.out.println("newFileName: "+newFileName);
                //将附件添加到文件夹中
                try {
                    attachment.transferTo(new java.io.File(myAttachment.getAttachmentPath() +"\\"+ newFileName));
                } catch (Exception e){
                    e.printStackTrace();
                    resultData.setCode("407");
                    resultData.setMsg("附件："+originalFileName + "，上传失败");
                    return resultData;
                }
                //将附件添加到数据库中
                Attachment newAttachment = new Attachment();
                newAttachment.setFileId(myAttachment.getFileId());
                newAttachment.setAttachmentName(originalFileName);  //name存储的是原文件名
                newAttachment.setAttachmentPath(filePath +"\\"+newFileName); //path存储的是路径和新文件名
                System.out.println("newAttachmentPath:"+newAttachment.getAttachmentPath());
                int result = attachmentDao.insert(newAttachment);
                if(result > 0){
                    resultData.setResult(ResultCodeEnum.ATTACHMENT_UPLOAD_SUCCESS);//上传附件为空
                } else {
                    resultData.setResult(ResultCodeEnum.ATTACHMENT_UPLOAD_FAILURE);//上传附件为空
                }
            }
        } else{
            resultData.setResult(ResultCodeEnum.FILE_UPLOAD_EMPTY);//上传附件为空
            return resultData;
        }
        return resultData;
    }

    @Transactional
    public ResultData modifyFileInfo(File file){
        resultData = new ResultData <Data>();
        if(file != null && file.getId() > 0) { //course不为空且id存在
            //文件信息校验
            if(!isFileExist(file.getId())) {
                resultData.setResult(ResultCodeEnum.DB_UPDATE_FAILURE_NOTICE_NOT_EXIST);//文件不存在
                return resultData;
            }
            //course外键校验
            if(!isCourseExist(file.getCourseId())) {
                resultData.setResult(ResultCodeEnum.DB_UPDATE_FAILURE_COURSE_NOT_EXIST);//课程不存在
                return resultData;
            }
            //teacher外键校验
            if(!isTeacherExist(file.getUploaderId())) {
                resultData.setResult(ResultCodeEnum.DB_UPDATE_FAILURE_TEACHER_NOT_EXIST);//教师不存在
                return resultData;
            }

            int result = fileDao.update(file);
            if(result > 0) {
                resultData.setResult(ResultCodeEnum.DB_UPDATE_SUCCESS); //修改成功
            } else {
                resultData.setResult(ResultCodeEnum.DB_UPDATE_ERROR);  //修改失败
            }
        } else {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL); //id直接返回数据必要参数为空
        }
        return resultData;
    }

    @Transactional
    public ResultData deleteFile(Integer id) {
        resultData = new ResultData <Data>();
        if(id != null) {
            //先删除附件
            Attachment attachment = new Attachment();
            attachment.setFileId(id);
            List<Attachment> attachments = attachmentDao.find(attachment);
            for(Attachment item : attachments){
                resultData = deleteAttachment(item);
                if(!resultData.getCode().equals("204")){
                    resultData.setResult(ResultCodeEnum.DB_DELETE_FAILURE);
                    return resultData;
                }
            }
            //再删除文档
            File file = new File();
            file.setId(Math.toIntExact(id));
            int result = fileDao.delete(file);
            if(result > 0) {
                resultData.setResult(ResultCodeEnum.DB_DELETE_SUCCESS);
            } else {
                resultData.setResult(ResultCodeEnum.DB_DELETE_FAILURE);
            }
        } else {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);  //必要参数为空
        }
        return resultData;
    }

    @Transactional
    public ResultData deleteAttachment(Attachment attachment) {
        resultData = new ResultData <Data>();
        if(attachment != null) {
            //先查询
            List<Attachment> attachments = attachmentDao.find(attachment);
            if(attachments.size() == 1) {
                attachment = attachments.get(0);
                //删除文件
                try{
                    java.io.File attachmentFile = new java.io.File(attachment.getAttachmentPath());
                    System.out.println(attachmentFile);
                    if (attachmentFile.exists() && attachmentFile.isFile()) {
                        if (attachmentFile.delete()) {
                            //删除表中字段
                            int result = attachmentDao.delete(attachment);
                            if(result > 0) {
                                resultData.setResult(ResultCodeEnum.DB_DELETE_SUCCESS); //删除成功
                            } else {
                                resultData.setResult(ResultCodeEnum.DB_DELETE_FAILURE); //删除失败
                            }
                        } else {
                            resultData.setResult(ResultCodeEnum.DB_DELETE_FAILURE); //下载失败
                            return resultData;
                        }
                    } else {
//						resultData.setResult(ResultCodeEnum.FILE_EMPTY); //附件不存在
                        int result = attachmentDao.delete(attachment);
                        if(result > 0) {
                            resultData.setResult(ResultCodeEnum.DB_DELETE_SUCCESS); //删除成功
                        } else {
                            resultData.setResult(ResultCodeEnum.DB_DELETE_FAILURE); //删除失败
                        }
                    }
                } catch (Exception e){//文件不存在
                    e.printStackTrace();
                    int result = attachmentDao.delete(attachment);
                    if(result > 0) {
                        resultData.setResult(ResultCodeEnum.DB_DELETE_SUCCESS); //删除成功
                    } else {
                        resultData.setResult(ResultCodeEnum.DB_DELETE_FAILURE); //删除失败
                    }
                }
            } else {
                resultData.setResult(ResultCodeEnum.FILE_EMPTY); //附件不存在
                return resultData;
            }
        } else {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);  //必要参数为空
        }
        return resultData;
    }

    @Transactional
    public ResultData deleteAssignment(Integer id) {
        resultData = new ResultData <Data>();
        if(id != null) {
            //先删除附件
            Attachment attachment = new Attachment();
            attachment.setFileId(id);
            List<Attachment> attachments = attachmentDao.find(attachment);
            for(Attachment item : attachments){
                resultData = deleteAttachment(item);
                if(!resultData.getCode().equals("204")){
                    resultData.setResult(ResultCodeEnum.DB_DELETE_FAILURE);
                    return resultData;
                }
            }
            //再删除文档
            File file = new File();
            file.setId(Math.toIntExact(id));
            int result = fileDao.delete(file);
            if(result > 0) {
                resultData.setResult(ResultCodeEnum.DB_DELETE_SUCCESS);
            } else {
                resultData.setResult(ResultCodeEnum.DB_DELETE_FAILURE);
            }
        } else {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);  //必要参数为空
        }
        return resultData;
    }

    @Transactional
    public ResultData deleteAssignmentFile(Attachment attachment) {
        resultData = new ResultData <Data>();
        if(attachment != null) {
            //先查询
            List<Attachment> attachments = attachmentDao.find(attachment);
            if(attachments.size() == 1) {
                attachment = attachments.get(0);
                //删除文件
                try{
                    java.io.File attachmentFile = new java.io.File(attachment.getAttachmentPath());
                    System.out.println(attachmentFile);
                    if (attachmentFile.exists() && attachmentFile.isFile()) {
                        if (attachmentFile.delete()) {
                            //删除表中字段
                            int result = attachmentDao.delete(attachment);
                            if(result > 0) {
                                resultData.setResult(ResultCodeEnum.DB_DELETE_SUCCESS); //删除成功
                            } else {
                                resultData.setResult(ResultCodeEnum.DB_DELETE_FAILURE); //删除失败
                            }
                        } else {
                            resultData.setResult(ResultCodeEnum.DB_DELETE_FAILURE); //下载失败
                            return resultData;
                        }
                    } else {
//						resultData.setResult(ResultCodeEnum.FILE_EMPTY); //附件不存在
                        int result = attachmentDao.delete(attachment);
                        if(result > 0) {
                            resultData.setResult(ResultCodeEnum.DB_DELETE_SUCCESS); //删除成功
                        } else {
                            resultData.setResult(ResultCodeEnum.DB_DELETE_FAILURE); //删除失败
                        }
                    }
                } catch (Exception e){//文件不存在
                    e.printStackTrace();
                    int result = attachmentDao.delete(attachment);
                    if(result > 0) {
                        resultData.setResult(ResultCodeEnum.DB_DELETE_SUCCESS); //删除成功
                    } else {
                        resultData.setResult(ResultCodeEnum.DB_DELETE_FAILURE); //删除失败
                    }
                }
            } else {
                resultData.setResult(ResultCodeEnum.FILE_EMPTY); //附件不存在
                return resultData;
            }
        } else {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);  //必要参数为空
        }
        return resultData;
    }

    public ResultData viewAssignment(File file, PageParam pageParam) {
        resultData = new ResultData <Data>();
        if(file != null) {
            if(pageParam != null && pageParam.isPaginate()){
                PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
            }
            List<FileDetail> files = fileDao.find(file);
            for(FileDetail index:files){
                System.out.println(index);
            }
            if(files.size() > 0) {
                Data <List<FileDetail>> data = new Data <List<FileDetail>>();
                if(pageParam != null && pageParam.isPaginate()){
                    PageInfo<FileDetail> pageInfo = new PageInfo<>(files);
                    data.setData((List <FileDetail>) pageInfo);
                    data.setData(pageInfo.getList());
                } else {
                    data.setData(files);
                }
                resultData.setData(data);
                resultData.setResult(ResultCodeEnum.DB_FIND_SUCCESS); //查找成功
            } else {
                resultData.setResult(ResultCodeEnum.DB_FIND_FAILURE); //查找失败
            }
        } else {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);  //必要请求参数为空
        }
        return resultData;
    }

    @Transactional
    public ResultData gradeAssignment(File file){
        resultData = new ResultData <Data>();
        if(file != null && file.getId() > 0) { //course不为空且id存在
            //文件信息校验
            if(!isFileExist(file.getId())) {
                resultData.setResult(ResultCodeEnum.DB_UPDATE_FAILURE_NOTICE_NOT_EXIST);//文件不存在
                return resultData;
            }
            //course外键校验
            if(!isCourseExist(file.getCourseId())) {
                resultData.setResult(ResultCodeEnum.DB_UPDATE_FAILURE_COURSE_NOT_EXIST);//课程不存在
                return resultData;
            }
            //teacher外键校验
            if(!isTeacherExist(file.getUploaderId())) {
                resultData.setResult(ResultCodeEnum.DB_UPDATE_FAILURE_TEACHER_NOT_EXIST);//教师不存在
                return resultData;
            }
            int result = fileDao.update(file);
            if(result > 0) {
                resultData.setResult(ResultCodeEnum.DB_UPDATE_SUCCESS); //修改成功
            } else {
                resultData.setResult(ResultCodeEnum.DB_UPDATE_ERROR);  //修改失败
            }
        } else {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL); //id直接返回数据必要参数为空
        }
        return resultData;
    }

    *//**
     * 判断课程是否存在
     * 外键约束条件，若课程不存在插入修改报错
     * @param courseId
     * @return 存在 true 不存在 false
     *//*
    private boolean isCourseExist(Integer courseId) {
        List<CourseDetail> courses = null;
        Course course = new Course();
        if(courseId != null) {
            course.setId(courseId);
            courses = courseDao.find(course);
        }
        return courseId != null && courses.size() == 1;
    }

    *//**
     * 判断老师是否存在
     * 外键约束条件，若老师不存在插入修改报错
     * @param teacherId
     * @return 存在 true 不存在 false
     *//*
    private boolean isTeacherExist(Integer teacherId) {
        List<Teacher> teachers = null;
        Teacher teacher = new Teacher();
        if(teacherId != null) {
            teacher.setId(teacherId);
            teachers = teacherDao.find(teacher);
        }
        return teacherId != null && teachers.size() > 0;
    }

    *//**
     * 判断公告是否存在
     * 外键约束条件，若公告不存在插入修改报错
     * @param noticeId
     * @return 存在 true 不存在 false
     *//*
    private boolean isNoticeExist(Integer noticeId) {
        List<NoticeDetail> notices = null;
        Notice notice = new Notice();
        if(noticeId != null) {
            notice.setId(Math.toIntExact(noticeId));
            notices = noticeDao.find(notice);
        }
        return notices != null && notices.size() > 0;
    }

    *//**
     * 判断文件是否存在
     * 外键约束条件，若公告不存在插入修改报错
     * @param fileId
     * @return 存在 true 不存在 false
     *//*
    private boolean isFileExist(Integer fileId) {
        List<FileDetail> files = null;
        File file = new File();
        if(fileId != null) {
            file.setId(Math.toIntExact(fileId));
            files = fileDao.find(file);
        }
        return files != null && files.size() > 0;
    }*/
}
