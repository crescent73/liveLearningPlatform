package com.java.filter;

import com.java.model.entity.LiveUser;
import com.java.utils.IpUtil;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class HandShakeInterceptor extends HttpSessionHandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("before handshake:"+request.getURI());
        // http协议转websock协议进行前，通常用这个拦截器判断用户的合法性等
        // 鉴别用户
        if(request instanceof ServerHttpRequest){
//            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpServletRequest httpServletRequest = ((ServletServerHttpRequest) request).getServletRequest();
            HttpSession session = httpServletRequest.getSession();
            LiveUser liveUser = (LiveUser) session.getAttribute("user");
            if (liveUser != null) {
                System.out.println("用户已登录");
                return super.beforeHandshake(request, response, wsHandler, attributes);
            } else {
                System.out.println("用户（未登录）");
                return false;
            }
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        // 握手成功后，通常来注册用户信息
        System.out.println("成功握手");
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
