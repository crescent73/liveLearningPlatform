package com.java.controller;

import com.java.constant.consist.Constant;
import com.java.model.entity.LiveUser;
import com.java.model.entity.Msg;
import com.java.service.intf.StatService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @MessageMapping("/onlineUser")// 服务端可以接收客户端通过向地址/online_user发送过来的消息
    @SendTo(Constant.ONLINE_USER_TOPIC)// 此方法会向订阅/topic/online_user的用户广播online_user的消息
    public List getOnlinePeople() {
        List allUserOnline = statService.getAllUserOnline();
        System.out.println(allUserOnline);
        return allUserOnline;
    }

    public void sendOnlinePeople(){
        simpMessagingTemplate.convertAndSend(Constant.ONLINE_USER_TOPIC,getOnlinePeople());// 通过后端推送的方式向/topic/online_user的用户广播online_user的消息
    }

    @MessageMapping("/guest")
    @SendTo(Constant.ALL_GUEST_TOPIC)
    public List getHistoryGuest() {
        List allGuestHistory = statService.getGuestHistory();
        return allGuestHistory;
    }
    public void sendAllGuest(){
        simpMessagingTemplate.convertAndSend(Constant.ALL_GUEST_TOPIC,getHistoryGuest());
    }

    @MessageMapping("/chat")
    @SendTo(Constant.MESSAGE_TOPIC)
    public Msg sendMessage(String message, @Header(value="simpSessionAttributes") Map<String,Object> session) {
        System.out.println(message);
        LiveUser liveUser = (LiveUser) session.get("user");
        String username = liveUser.getRandomName();
        Msg msg = new Msg();
        msg.setCreator(username);
        msg.setSTime(Calendar.getInstance());
        msg.setMsgBody(message);
        return msg;
    }

    @MessageMapping("change-notice")
    @SendTo("/topic/notice")
    public String greeting(String message){
        System.out.println("服务端收到消息！！！！！！"+message.toString());
        return message;
    }
}
