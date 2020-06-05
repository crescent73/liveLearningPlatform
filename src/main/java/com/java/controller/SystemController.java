package com.java.controller;

import com.java.constant.enums.ResultCodeEnum;
import com.java.model.entity.*;
import com.java.model.vo.ResultData;
import com.java.service.intf.LiveUserService;
import com.java.service.intf.SystemService;
import com.java.utils.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private LiveUserService liveUserService;

    @Autowired
    private LiveController liveController;

    @Autowired
    private SystemService systemService;

    @RequestMapping("/register")
    public ResultData register(User user) {
        System.out.println(user);
        ResultData resultData = new ResultData();
        if (user.getUserRole()==null || user.getUserNickname()== null || user.getUserPassword()==null
                || user.getUserGender()==null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        int result = systemService.register(user);
        if (result>0){
            resultData.setResult(ResultCodeEnum.OK);
        } else if (result == -2){
            resultData.setResult(ResultCodeEnum.USER_HAVE_EXIST);
        } else {
            resultData.setResult(ResultCodeEnum.REGISTER_ERROR);
        }
         return resultData;
    }

    @RequestMapping("/login")
    public ResultData login(String name, String password, String userType, HttpServletRequest request) {
        System.out.println(name+"-"+password+"-"+userType);
        ResultData resultData = new ResultData();
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
    public ResultData logout(Long id, HttpSession session){
        ResultData resultData = new ResultData();
        if(id!=null){//判断是否为空字符串
            if(session.getAttribute("user")==null){//获取属性
                resultData.setResult(ResultCodeEnum.NO_LOGIN_USER);
            } else {
                session.removeAttribute("user");
                resultData.setResult(ResultCodeEnum.LOGOUT_SUCCESS);//成功退出
            }
        }else{
            resultData = new ResultData();
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
        }
        return resultData;
    }

    @RequestMapping("/modifyInfo")
    public ResultData modifyInfo(User user) {
        ResultData resultData = new ResultData();
        if(user.getUserId() != null ) {
            try{
                int i = systemService.modifyInfo(user);
                if (i>0) {
                    resultData.setResult(ResultCodeEnum.OK);
                } else {
                    resultData.setResult(ResultCodeEnum.DB_UPDATE_ERROR);
                }
            }catch(Exception e){
                e.printStackTrace();
                resultData = new ResultData();
                resultData.setResult(ResultCodeEnum.SERVER_ERROR);
            }
        } else {
            resultData = new ResultData();
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
        }
        return resultData;
    }



    @RequestMapping("/connect")
    public ResultData connect(HttpServletRequest request,Integer courseScheduleId){
        ResultData resultData;
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
        List allUserOnline = liveController.getOnlinePeople(courseScheduleId);
        List allGuestHistory = liveController.getHistoryGuest(courseScheduleId);
        Map<String,List> all = new HashMap<String,List>();
        all.put("allUserOnline",allUserOnline);
        all.put("allGuestHistory",allGuestHistory);
        resultData = new ResultData <List>();
        resultData.setResult(ResultCodeEnum.OK);
        resultData.setData(all);
        return resultData;
    }
}
