package com.java.controller;

import com.java.constant.consist.Constant;
import com.java.model.entity.LiveUser;
import com.java.model.entity.Msg;
import com.java.service.intf.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@RestController
public class LiveController {
    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private StatService statService;

    @MessageMapping("/onlineUser.{courseScheduleId}")// 服务端可以接收客户端通过向地址/online_user发送过来的消息
    @SendTo(Constant.ONLINE_USER_TOPIC+".{courseScheduleId}")// 此方法会向订阅/topic/online_user的用户广播online_user的消息
    public List getOnlinePeople(@DestinationVariable Integer courseScheduleId) {
        List allUserOnline = statService.getAllUserOnline(courseScheduleId);
        return allUserOnline;
    }

    public void sendOnlinePeople(Integer courseScheduleId){
        System.out.println("getOnlinePeople!!!"+getOnlinePeople(courseScheduleId));
        simpMessagingTemplate.convertAndSend(Constant.ONLINE_USER_TOPIC+"."+courseScheduleId,getOnlinePeople(courseScheduleId));// 通过后端推送的方式向/topic/online_user的用户广播online_user的消息
    }

    @MessageMapping("/guest.{courseScheduleId}")
    @SendTo(Constant.ALL_GUEST_TOPIC+".{courseScheduleId}")
    public List getHistoryGuest(@DestinationVariable Integer courseScheduleId) {
        List allGuestHistory = statService.getGuestHistory(courseScheduleId);
        return allGuestHistory;
    }
    public void sendAllGuest(Integer courseScheduleId){
        simpMessagingTemplate.convertAndSend(Constant.ALL_GUEST_TOPIC+"."+courseScheduleId,getHistoryGuest(courseScheduleId));
    }

    @MessageMapping("/chat.{courseScheduleId}")
    @SendTo(Constant.MESSAGE_TOPIC+".{courseScheduleId}")
    public Msg sendMessage(String message, @Header(value="simpSessionAttributes") Map<String,Object> session,@DestinationVariable Integer courseScheduleId) {
        LiveUser liveUser = (LiveUser) session.get("user");
        String username = liveUser.getRandomName();
        Msg msg = new Msg();
        msg.setCreator(username);
        msg.setSTime(Calendar.getInstance());
        msg.setMsgBody(message);
        return msg;
    }

    @MessageMapping("change-notice.{courseScheduleId}")
    @SendTo("/topic/notice.{courseScheduleId}")
    public String greeting(String message,@DestinationVariable Integer courseScheduleId){
        System.out.println("服务端收到消息！！！！！！"+message.toString());
        return message;
    }
}
