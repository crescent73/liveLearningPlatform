package com.java.filter;

import com.java.dao.LiveUserDao;
import com.java.dao.UserDao;
import com.java.model.entity.LiveUser;
import com.java.utils.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private LiveUserDao liveUserDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = IpUtil.getIp(request);
        HttpSession session = request.getSession();
        LiveUser user = liveUserDao.findOne(ip);
        if(user != null) {
            System.out.println("用户曾经访问过");
        } else {
            System.out.println("曾经访问过");
            user = new LiveUser();
            user.setIp(ip);
            user.setRandomName("hello");
            liveUserDao.insert(user);
        }
        session.setAttribute("user",user);

        return true;
    }
}
