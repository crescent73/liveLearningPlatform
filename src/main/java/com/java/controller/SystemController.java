package com.java.controller;

import com.java.constant.enums.ResultCodeEnum;
import com.java.dao.LiveUserDao;
import com.java.model.entity.LiveUser;
import com.java.model.entity.Msg;
import com.java.model.entity.User;
import com.java.model.vo.ResultData;
import com.java.service.intf.LiveUserService;
import com.java.service.intf.SystemService;
import com.java.utils.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private LiveUserService liveUserService;

    private ResultData resultData;
    @Autowired
    private LiveController liveController;

    @Autowired
    private SystemService systemService;

    @RequestMapping("/login")
    public ResultData login(String name,String password,String userType,HttpServletRequest request) {
        System.out.println(name+"-"+password+"-"+userType);
        resultData = new ResultData();
        User user = new User();
        user.setUserNickname(name);
        user.setUserPassword(password);
        user.setUserRole(userType);
        user = systemService.login(user);
        if (user!=null){
            HttpSession session = request.getSession();
            LiveUser liveUser = new LiveUser();
            liveUser.setIp(IpUtil.getIp(request));
            liveUser.setRandomName(user.getUserNickname());
            liveUserService.insert(liveUser);
            session.setAttribute("user",liveUser);
            resultData.setResult(ResultCodeEnum.OK);
            resultData.setData(user);
        } else {
            resultData.setResult(ResultCodeEnum.LOGIN_ERROR);
        }
        return resultData;
    }

    @RequestMapping("/logout")
    public ResultData logout(){
        return null;
    }

    @RequestMapping("/modifyInfo")
    public ResultData modifyInfo(){
        return null;
    }

    @RequestMapping("/register")
    public ResultData register(){
        return null;
    }

    @RequestMapping("/connect")
    public ResultData connect(HttpServletRequest request){
        String ip = IpUtil.getIp(request);
        HttpSession session = request.getSession();
        LiveUser user = (LiveUser) session.getAttribute("user");
        if(user != null) {
            System.out.println("已登录");
        } else {
            System.out.println("用户未登录！");
            user = new LiveUser();
            user.setIp(ip);
            user.setRandomName("guest");
            liveUserService.insert(user);
            session.setAttribute("user",user);
        }
        List allUserOnline = liveController.getOnlinePeople();
        List allGuestHistory = liveController.getHistoryGuest();
        Map<String,List> all = new HashMap<>();
        all.put("allUserOnline",allUserOnline);
        all.put("allGuestHistory",allGuestHistory);
        resultData = new ResultData<List>();
        resultData.setResult(ResultCodeEnum.OK);
        resultData.setData(all);
        return resultData;
    }
}
