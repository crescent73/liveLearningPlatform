package com.java.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.java.constant.enums.ResultCodeEnum;
import com.java.dao.*;
import com.java.model.entity.*;
import com.java.model.vo.Data;
import com.java.model.vo.ResultData;
import com.java.service.intf.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class SystemServiceImpl implements SystemService {
    private ResultData <Data> resultData;

    @Autowired
    private UserDao userDao;

    @Autowired
    private NoticeDao noticeDao;

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private FileDao fileDao;

    @Autowired
    private AttachmentDao attachmentDao;

    @Override
    public ResultData register(User user) {
        //调用DAO存储数据
        UserDao.addUser(user);
        return resultData;
    }

    /**
     * 登录
     * 检查条件：
     * name，password，userType
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        QueryWrapper<User> userSelect = new QueryWrapper<User>();
        userSelect.eq("user_nickname",user.getUserNickname());
        userSelect.eq("user_password",user.getUserPassword());
        userSelect.eq("user_role", user.getUserRole());
        List<User> loginUser = userDao.selectList(userSelect);
        if (loginUser.size()>0){
            return loginUser.get(0);
        } else {
            return null;
        }
    }

    @Override
    public ResultData logout(Long id, String userType) {
        resultData = new ResultData <Data>();
        switch(userType) {
            case "1":
                //调用adminDao
                break;
            case "2":
                //调用teacherDao
                break;
            case "3":
                //调用studentDao
                break;
            default:
                //返回错误信息
                resultData.setResult(ResultCodeEnum.PARA_FORMAT_ERROR); //参数格式错误
                return resultData;
        }
        resultData.setResult(ResultCodeEnum.LOGOUT_SUCCESS);
        return resultData;
    }
    @Transactional
    public ResultData modifyInfo(Integer id, String password, String userType, HttpSession httpSession) {
        resultData = new ResultData <Data>();
        if(id!=null) {
            int result;
            switch(userType) {
                case "1":
                    //调用adminDao
                    Admin admin = new Admin();
                    admin.setId(id);
                    admin.setAdminPassword(password);
                    result = AdminDao.update(admin);
                    break;
                case "2":
                    //调用teacherDao
                    Teacher teacher = new Teacher();
                    teacher.setId(id);
                    teacher.setTeacherPassword(password);
                    System.out.println(teacher);
                    result = teacherDao.update(teacher);
                    break;
                case "3":
                    //调用studentDao
                    Student student = new Student();
                    student.setId(id);
                    student.setStudentPassword(password);
                    result = StudentDao.update(student);
                    break;
                default:
                    //返回错误信息
                    resultData.setResult(ResultCodeEnum.PARA_FORMAT_ERROR); //参数格式错误
                    return resultData;
            }
            if(result > 0) {
                resultData.setResult(ResultCodeEnum.DB_UPDATE_SUCCESS); //更新成功
                httpSession.removeAttribute("login");
                httpSession.removeAttribute("user");
            } else {
                resultData.setResult(ResultCodeEnum.DB_UPDATE_ERROR); // 更新失败
            }
        } else {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL); //必要请求参数为空
        }

        return resultData;
    }

    @Transactional
    @Override
    //教师和学生公用
    public ResultData getNoticeList(Notice notice, PageParam pageParam) {
        resultData = new ResultData <Data>();
        if(notice != null) {
            if(pageParam != null && pageParam.isPaginate()){
                PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
            }
            List<NoticeDetail> notices = noticeDao.find(notice);
            System.out.println(notices);
            if(notices.size()>0) {
                Data <List<NoticeDetail>> data = new Data <List<NoticeDetail>>();
                if(pageParam != null && pageParam.isPaginate()){
                    PageInfo<NoticeDetail> pageInfo = new PageInfo<>(notices);
                    data.setData((List <NoticeDetail>) pageInfo);
                    data.setData(pageInfo.getList());
                } else {
                    data.setData(notices);
                }
                resultData.setData(data);
                resultData.setResult(ResultCodeEnum.DB_FIND_SUCCESS); //查找成功
            } else {
                resultData.setResult(ResultCodeEnum.DB_FIND_FAILURE); //查找失败
            }
        } else {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL); //必要请求参数为空
        }
        return resultData;
    }

    @RequestMapping("/searchFile")
    @ResponseBody
    //学生教师公用
    public ResultData searchFile(File file, PageParam pageParam) {
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
    public AttachmentDetail downloadAttachment(Attachment attachment) {
        resultData = new ResultData <Data>();
        if(attachment != null){
            List<Attachment> attachments = attachmentDao.find(attachment);
            if(attachments.size() == 1) {
                AttachmentDetail attachmentDetail = new AttachmentDetail();
                attachmentDetail.setAttachmentInfo(attachments.get(0));

                java.io.File attachmentFile = new java.io.File(attachmentDetail.getAttachmentPath());
                System.out.println("attachmentDetail"+attachmentDetail);
                attachmentDetail.setFile(attachmentFile);

                return attachmentDetail;
            } else {
                return null; //未找到对应文件，返回为空
            }
        }
        return null; //确实必要参数返回为空
    }

    @RequestMapping("/getAssignmentList")
    @ResponseBody
    //学生教师公用，作业以文件的形式呈现
    public ResultData getAssignmentList(File file, PageParam pageParam) {
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
    public AttachmentDetail getAssignmentDetail(Attachment attachment) {
        resultData = new ResultData <Data>();
        if(attachment != null){
            List<Attachment> attachments = attachmentDao.find(attachment);
            if(attachments.size() == 1) {
                AttachmentDetail attachmentDetail = new AttachmentDetail();
                attachmentDetail.setAttachmentInfo(attachments.get(0));

                java.io.File attachmentFile = new java.io.File(attachmentDetail.getAttachmentPath());
                System.out.println("attachmentDetail"+attachmentDetail);
                attachmentDetail.setFile(attachmentFile);

                return attachmentDetail;
            } else {
                return null; //未找到对应文件，返回为空
            }
        }
        return null; //确实必要参数返回为空
    }
}
