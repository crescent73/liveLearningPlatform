package com.java.service.impl;

import com.java.constant.enums.ResultCodeEnum;
import com.java.dao.AttachmentDao;
import com.java.dao.CourseDao;
import com.java.dao.FileDao;
import com.java.dao.TeacherDao;
import com.java.model.entity.*;
import com.java.model.vo.Data;
import com.java.model.vo.ResultData;
import com.java.service.intf.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class StudentServiceImpl implements StudentService {
    private ResultData <Data> resultData;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private FileDao fileDao;

    @Autowired
    private AttachmentDao attachmentDao;

    @Transactional
    public ResultData submitAssignment(File file, String dirPath, List<MultipartFile> attachments) {
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
    /**
     * 判断课程是否存在
     * 外键约束条件，若课程不存在插入修改报错
     * @param courseId
     * @return 存在 true 不存在 false
     */
    private boolean isCourseExist(Integer courseId) {
        List<CourseDetail> courses = null;
        Course course = new Course();
        if(courseId != null) {
            course.setId(courseId);
            courses = courseDao.find(course);
        }
        return courseId != null && courses.size() == 1;
    }

    /**
     * 判断老师是否存在
     * 外键约束条件，若老师不存在插入修改报错
     * @param teacherId
     * @return 存在 true 不存在 false
     */
    private boolean isTeacherExist(Integer teacherId) {
        List<Teacher> teachers = null;
        Teacher teacher = new Teacher();
        if(teacherId != null) {
            teacher.setId(teacherId);
            teachers = teacherDao.find(teacher);
        }
        return teacherId != null && teachers.size() > 0;
    }

}
