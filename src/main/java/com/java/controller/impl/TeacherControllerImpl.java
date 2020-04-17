package com.java.controller.impl;

import com.java.constant.enums.ResultCodeEnum;
import com.java.controller.intf.TeacherController;
import com.java.model.entity.Course;
import com.java.model.entity.LiveUser;
import com.java.model.entity.Msg;
import com.java.model.vo.Data;
import com.java.model.vo.ResultData;
import com.java.service.intf.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teacher")
public class TeacherControllerImpl implements TeacherController {

    private ResultData resultData;
    @Autowired
    private StatService statService;

    @RequestMapping("/searchCourse")
    public ResultData getCourseList(Course course) {
        return null;
    }

    @RequestMapping("/addCourse")
    public ResultData createCourse() {
        return null;
    }

    @RequestMapping("/deleteCourse")
    public ResultData deleteCourse() {
        return null;
    }

    @RequestMapping("/setCoursePermission")
    public ResultData setCoursePermission() {
        return null;
    }

    @RequestMapping("/getCollectionCourseList")
    public ResultData collectCourseList() {
        return null;
    }

    @RequestMapping("/addCollectCourse")
    public ResultData addCollectCourse() {
        return null;
    }

    @RequestMapping("/deleteCollectCourse")
    public ResultData deleteCollectCourse() {
        return null;
    }

    @RequestMapping("/getCourseVideoList")
    public ResultData getVideoList() {
        return null;
    }

    @RequestMapping("/getCourseVideo")
    public ResultData getVideo() {
        return null;
    }

    @RequestMapping("/deleteCourseVideo")
    public ResultData deleteVideo() {
        return null;
    }

    @RequestMapping("/sendLivingStream")
    public ResultData sendLivingStream() {
        return null;
    }

    @RequestMapping("/publishSign")
    public ResultData publishSign() {
        return null;
    }

    @RequestMapping("/getSignDetail")
    public ResultData getSignDetail() {
        return null;
    }

    @RequestMapping("/getOnlinePeople")
    public ResultData getOnlinePeople() {
        resultData = new ResultData<List>();
        List allUserOnline = statService.getAllUserOnline();
        resultData.setResult(ResultCodeEnum.OK);
        resultData.setData(allUserOnline);
        return resultData;
    }

    @RequestMapping("/getHistoryGuest")
    public ResultData getHistoryGuest() {
        resultData = new ResultData<List>();
        List allGuestHistory = statService.getGuestHistory();
        resultData.setResult(ResultCodeEnum.OK);
        resultData.setData(allGuestHistory);
        return resultData;
    }

    @RequestMapping("/getMessageList")
    public ResultData getMessageList() {
        return null;
    }

    @RequestMapping("/sendMessage")
    @SendTo("/topic/group")
    public ResultData sendMessage(String message, @Header(value="simpSessionAttributes") Map<String,Object> session) {
        LiveUser liveUser = (LiveUser) session.get("user");
        String username = liveUser.getRandomName();
        Msg msg = new Msg();
        msg.setCreator(username);
        msg.setSTime(Calendar.getInstance());
        msg.setMsgBody(message);
        resultData = new ResultData<List>();
        resultData.setResult(ResultCodeEnum.OK);
        resultData.setData(msg);
        return resultData;
    }

    @RequestMapping("/getNoticeList")
    public ResultData getNoticeList() {
        return null;
    }

    @RequestMapping("/addNotice")
    public ResultData publishNotice() {
        return null;
    }

    @RequestMapping("/modifyNotice")
    public ResultData modifyNotice() {
        return null;
    }

    @RequestMapping("/deleteNotice")
    public ResultData deleteNotice() {
        return null;
    }

    @RequestMapping("/getAssignmentList")
    public ResultData getAssignmentList() {
        return null;
    }

    @RequestMapping("/getAssignmentDetail")
    public ResultData getAssignmentDetail() {
        return null;
    }

    @RequestMapping("/publishAssignment")
    public ResultData publishAssignment() {
        return null;
    }

    @RequestMapping("/gradeAssignment")
    public ResultData gradeAssignment() {
        return null;
    }

    @RequestMapping("/deleteAssignment")
    public ResultData deleteAssignment() {
        return null;
    }

    @RequestMapping("/addFile")
    public ResultData uploadFile() {
        return null;
    }

    @RequestMapping("/searchFile")
    public ResultData getFileList() {
        return null;
    }

    @RequestMapping("/downloadFile")
    public ResultData downloadFile() {
        return null;
    }

    @RequestMapping("/modifyFile")
    public ResultData modifyFileInfo() {
        return null;
    }

    @RequestMapping("/deleteFile")
    public ResultData deleteFile() {
        return null;
    }
}
