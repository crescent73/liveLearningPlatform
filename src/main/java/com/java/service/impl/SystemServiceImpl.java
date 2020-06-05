package com.java.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.dao.*;
import com.java.model.entity.*;
import com.java.service.intf.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SystemServiceImpl implements SystemService {
    @Autowired
    private UserDao userDao;

    @Override
    public int register(User user) {
        QueryWrapper<User> userSelect = new QueryWrapper<User>();
        userSelect.eq("user_nickname",user.getUserNickname());
        List<User> users = userDao.selectList(userSelect);
        if (users.size()>0) {
            return -2;
        }
        //调用DAO存储数据
        int insert = userDao.insert(user);
        return insert;
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
    public int logout(Long id, String userType) {
        return -1;
    }

    @Override
    public int modifyInfo(User user) {
        int i = userDao.updateById(user);
        return i;
    }
   /* @Transactional

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
    }*/
}
