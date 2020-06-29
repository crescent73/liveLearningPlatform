package com.java.filter;

import com.java.constant.consist.Constant;
import com.java.controller.LiveController;
import com.java.model.entity.Guest;
import com.java.model.entity.LiveUser;
import com.java.service.intf.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import java.util.*;

public class MyChannelInterceptor implements ChannelInterceptor {
    @Autowired
    private StatService statService;
    @Autowired
    private LiveController liveController;

    @Override
    public boolean preReceive(MessageChannel channel) {
//        System.out.println("preReceive");
        return true;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        System.out.println("------------------preSend----------------------");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();
        //检测用户订阅内容（防止用户订阅不合法频道）
        if (StompCommand.SUBSCRIBE.equals(command)) {
            //从数据库获取用户订阅频道进行对比(这里为了演示直接使用set集合代替)
//            System.out.println("subscribe!"+accessor.getDestination());
            Set<String> subedChannelInDB = new HashSet<>();
            subedChannelInDB.add(Constant.ALL_GUEST_TOPIC);
            subedChannelInDB.add(Constant.MESSAGE_TOPIC);
            subedChannelInDB.add(Constant.ONLINE_USER_TOPIC);
            subedChannelInDB.add("/topic/notice");// 测试的topic
            subedChannelInDB.add("/topic/sign");// 测试的topic
            String perfix = "";
            if (accessor.getDestination() != null) {
                String[] split = accessor.getDestination().split("\\.");
//                System.out.println(Arrays.toString(split));
                perfix = split[0];
//                System.out.println(subedChannelInDB.contains(perfix));
            }
            if (subedChannelInDB.contains(perfix)) {
                //该用户订阅的频道合法
                return message;
            } else {
                //该用户订阅的频道不合法直接返回null前端用户就接受不到该频道信息。
                return null;
            }
        } else {
            return message;
        }
    }

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        System.out.println("-----------afterSendCompletion--------------------");
        //检测用户是否连接成功，搜集在线的用户信息如果数据量过大我们可以选择使用缓存数据库比如redis,
        //这里由于需要频繁的删除和增加集合内容，我们选择set集合来存储在线用户
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();
        System.out.println("命令="+command);
        System.out.println("destination"+accessor.getDestination());
        Integer courseScheduleId = null;
//        System.out.println(accessor.getDestination());
        if (accessor.getDestination()!=null) {
            String[] split = accessor.getDestination().split("\\.");
//            System.out.println("?????????????"+Arrays.toString(split));
            courseScheduleId = Integer.parseInt(split[1]);
        }
//        System.out.println("courseScheduleId:"+courseScheduleId);
        if (StompCommand.SUBSCRIBE.equals(command)){
            Map<String,LiveUser> map = (Map<String, LiveUser>) accessor.getHeader("simpSessionAttributes");
            LiveUser user = map.get("user");
            if(user != null && courseScheduleId != null){
                System.out.println("添加在线人数！");
                statService.pushOnlineUser(user,courseScheduleId);
                Guest guest = new Guest();
                guest.setLiveUser(user);
                guest.setAccessTime(Calendar.getInstance().getTimeInMillis());
                statService.pushGuestHistory(guest,courseScheduleId);
//                System.out.println("sendOnlinePeople---courseScheduleId"+courseScheduleId);
                //通过websocket实时返回在线人数
                liveController.sendOnlinePeople(courseScheduleId);//广播在线人数
            }
        }
        //如果用户断开连接，删除用户信息
        if (StompCommand.DISCONNECT.equals(command)){
            Map<String,LiveUser> map = (Map<String, LiveUser>) accessor.getHeader("simpSessionAttributes");
            LiveUser user = map.get("user");
            if (user != null && courseScheduleId != null){
                statService.popOnlineUser(user,courseScheduleId);
//                System.out.println("sendOnlinePeople--delete---courseScheduleId"+courseScheduleId);
                liveController.sendOnlinePeople(courseScheduleId);// 更新在线人数
            }

        }
    }

}
