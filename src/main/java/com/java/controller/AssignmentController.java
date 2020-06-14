package com.java.controller;

import com.java.constant.enums.ResultCodeEnum;
import com.java.model.entity.Assignment;
import com.java.model.entity.StudentAssignment;
import com.java.model.vo.ResultData;
import com.java.service.intf.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Component
public class AssignmentController {
    @Autowired
    private AssignmentService assignmentService;

    public ResultData getAssignmentList(Assignment assignment) {
        ResultData<List<Assignment>> resultData = new ResultData <>();
        if (assignment.getCourseId() == null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        List<Assignment> assignmentList = assignmentService.getAssignmentList(assignment);
        if (assignmentList!=null){
            resultData.setResult(ResultCodeEnum.OK);
            resultData.setData(assignmentList);
        } else {
            resultData.setResult(ResultCodeEnum.DB_FIND_FAILURE);
        }
        return resultData;
    }

    public ResultData getAssignmentListDetail(Assignment assignment) {
        ResultData<Assignment> resultData = new ResultData <>();
        if (assignment.getAssignmentId() == null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        Assignment assignmentDetail = assignmentService.getAssignmentDetail(assignment);
        if (assignmentDetail!=null){
            resultData.setResult(ResultCodeEnum.OK);
            resultData.setData(assignmentDetail);
        } else {
            resultData.setResult(ResultCodeEnum.DB_FIND_FAILURE);
        }
        return resultData;
    }


    public ResultData publishAssignment(Assignment assignment) {
        ResultData<Assignment> resultData = new ResultData <>();
        if (assignment.getCourseId() == null || assignment.getUserId() == null
                ||assignment.getAssignmentTitle() == null || assignment.getAssignmentContent() == null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        int result = assignmentService.publishAssignment(assignment);
        if (result>0){
            resultData.setResult(ResultCodeEnum.OK);
        } else {
            resultData.setResult(ResultCodeEnum.DB_ADD_FAILURE);
        }
        return resultData;
    }

    public ResultData gradeAssignment(StudentAssignment studentAssignment){
        ResultData<StudentAssignment> resultData = new ResultData <>();
        if (studentAssignment.getStudentAssignmentId() == null || studentAssignment.getScore() == null
                || studentAssignment.getTeacherReply() == null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        int result = assignmentService.gradeAssignment(studentAssignment);
        if (result>0){
            resultData.setResult(ResultCodeEnum.OK);
        } else {
            resultData.setResult(ResultCodeEnum.DB_UPDATE_ERROR);
        }
        return resultData;
    }

    public ResultData deleteAssignment(Assignment assignment) {
        ResultData<Assignment> resultData = new ResultData <>();
        if (assignment.getAssignmentId()==null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        int result = assignmentService.deleteAssignment(assignment);
        if (result>0){
            resultData.setResult(ResultCodeEnum.OK);
        } else {
            resultData.setResult(ResultCodeEnum.DB_DELETE_FAILURE);
        }
        return resultData;
    }

    public ResultData submitAssignment(StudentAssignment studentAssignment){
        ResultData<StudentAssignment> resultData = new ResultData <>();
        if (studentAssignment.getUserId() == null || studentAssignment.getAssignmentId() == null
                ||studentAssignment.getAssignmentSubmission() == null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        int result = assignmentService.submitAssignment(studentAssignment);
        if (result>0){
            resultData.setResult(ResultCodeEnum.OK);
        } else {
            resultData.setResult(ResultCodeEnum.DB_ADD_FAILURE);
        }
        return resultData;
    }
}
