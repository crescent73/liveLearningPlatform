package com.java.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.dao.AssignmentDao;
import com.java.dao.StudentAssignmentDao;
import com.java.model.entity.Assignment;
import com.java.model.entity.StudentAssignment;
import com.java.service.intf.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    private AssignmentDao assignmentDao;
    @Autowired
    private StudentAssignmentDao studentAssignmentDao;

    @Override
    public List<Assignment> getAssignmentList(Assignment assignment) {
        QueryWrapper<Assignment> assignmentQueryWrapper = new QueryWrapper<>();
        assignmentQueryWrapper.eq(assignment.getAssignmentId()!=null,"assignment_id",assignment.getAssignmentId());
        assignmentQueryWrapper.eq("course_id",assignment.getCourseId());
        assignmentQueryWrapper.eq(assignment.getUserId()!=null,"user_id",assignment.getUserId());
        assignmentQueryWrapper.eq(assignment.getAssignmentTitle()!=null,"assignment_title",assignment.getAssignmentTitle());
        assignmentQueryWrapper.eq(assignment.getAssignmentContent()!=null,"assignment_content",assignment.getAssignmentContent());
        List<Assignment> assignments = assignmentDao.selectList(assignmentQueryWrapper);
        if (assignments.size()>0) {
            return assignments;
        } else {
            return null;
        }
    }

    @Override
    public Assignment getAssignmentDetail(Assignment assignment) {
        Assignment assignment1 = assignmentDao.selectById(assignment.getAssignmentId());
        return assignment1;
    }

    @Override
    public int publishAssignment(Assignment assignment) {
        int insert = assignmentDao.insert(assignment);
        return insert;
    }

    @Override
    public int gradeAssignment(StudentAssignment studentAssignment) {
        int result = studentAssignmentDao.updateById(studentAssignment);
        return result;
    }

    @Override
    public int deleteAssignment(Assignment assignment) {
        int result = assignmentDao.deleteById(assignment.getAssignmentId());
        return result;
    }

    @Override
    public int submitAssignment(StudentAssignment studentAssignment) {
        int insert = studentAssignmentDao.insert(studentAssignment);
        return insert;
    }
}
