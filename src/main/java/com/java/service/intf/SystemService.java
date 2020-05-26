package com.java.service.intf;

import com.java.model.entity.*;
import com.java.model.vo.ResultData;

import javax.servlet.http.HttpSession;

public interface SystemService {
    // 注册
    public ResultData register(User user);
    // 登录
    User login(User user);
    // 退出
    public ResultData logout(Long id, String userType);
    // 修改信息
    public ResultData modifyInfo(Integer id, String password, String userType, HttpSession session);

    /*//查询课程公告列表（Student和Teacher公用）
    public ResultData getNoticeList(Notice notice, PageParam pageParam);

    //获取课程文档列表（Student和Teacher公用）
    public ResultData searchFile(File file, PageParam pageParam);

    //获取作业列表（Student和Teacher公用），作业以文件的形式呈现
    public ResultData getAssignmentList(File file, PageParam pageParam);*/

}
