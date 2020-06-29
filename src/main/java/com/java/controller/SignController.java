package com.java.controller;

import com.java.constant.enums.ResultCodeEnum;
import com.java.model.dto.SignDetail;
import com.java.model.entity.Sign;
import com.java.model.entity.SignStudent;
import com.java.model.vo.ResultData;
import com.java.service.intf.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Component
public class SignController {
    @Autowired
    private SignService signService;

    public ResultData sign(Integer signId, Integer studentId) {
        ResultData resultData = new ResultData();
        if (signId==null||studentId==null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        SignStudent signStudent = new SignStudent();
        signStudent.setStudentId(studentId);
        signStudent.setSignId(signId);
        int result = signService.sign(signStudent);
        if (result>0){
            resultData.setResult(ResultCodeEnum.OK);
        } else if (result == -2){
            resultData.setResult(ResultCodeEnum.SIGN_NOT_EXIST);
        } else if (result == -3){
            resultData.setResult(ResultCodeEnum.STUDENT_NOT_EXIST);
        } else{
            resultData.setResult(ResultCodeEnum.DB_SIGN_FAILURE);
        }
        return resultData;
    }

    public ResultData publishSign(Integer courseId, Integer teacherId, Integer courseScheduleId) {
        ResultData resultData = new ResultData();
        // 非空判断
        if (courseId == null || teacherId == null || courseScheduleId == null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        // 传入参数
        Sign sign = new Sign();
        sign.setCourseId(courseId);
        sign.setUserId(teacherId);
        sign.setCourseScheduleId(courseScheduleId);
        int result = signService.publishSign(sign);
        if (result>0){
            resultData.setResult(ResultCodeEnum.OK);
            resultData.setData(result);
        } else if (result == -2){
            resultData.setResult(ResultCodeEnum.COURSE_NOT_EXIST);
        } else if (result == -3){
            resultData.setResult(ResultCodeEnum.STUDENT_NOT_EXIST);
        } else if (result == -4){
            resultData.setResult(ResultCodeEnum.COURSE_SCHEDULE_NOT_EXIST);
        } else{
            resultData.setResult(ResultCodeEnum.DB_ADD_FAILURE);
        }
        return resultData;
    }

    public ResultData getSignList(Integer courseId, Integer teacherId, Integer courseScheduleId) {
        ResultData <String> resultData = new ResultData <>();
        // 非空判断
        if (courseScheduleId == null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        // 传入参数
        Sign sign = new Sign();
        sign.setCourseId(courseId);
        sign.setUserId(teacherId);
        if (courseScheduleId!=null){
            sign.setCourseScheduleId(courseScheduleId);
        }
        String signDetail = signService.getSignList(sign);
        if (signDetail!=null){
            resultData.setResult(ResultCodeEnum.OK);
            resultData.setData(signDetail);
        } else {
            resultData.setResult(ResultCodeEnum.DB_FIND_FAILURE);
        }
        return resultData;
    }

    public ResultData getSignDetail(Integer signId) {
        ResultData <List<SignDetail>> resultData = new ResultData <>();
        // 非空判断
        if (signId == null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        // 传入参数
        SignStudent signStudent = new SignStudent();
        signStudent.setSignId(signId);
        List<SignDetail> signDetail = signService.getSignDetail(signStudent);
        if (signDetail!=null){
            resultData.setResult(ResultCodeEnum.OK);
            resultData.setData(signDetail);
        } else {
            resultData.setResult(ResultCodeEnum.DB_FIND_FAILURE);
        }
        return resultData;
    }
}
