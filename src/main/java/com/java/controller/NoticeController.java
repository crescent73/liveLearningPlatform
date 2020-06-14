package com.java.controller;

import com.java.constant.enums.ResultCodeEnum;
import com.java.model.entity.Notice;
import com.java.model.vo.ResultData;
import com.java.service.intf.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Component
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    public ResultData getNoticeList(Notice notice) {
        ResultData <List<Notice>> resultData = new ResultData <>();
        if (notice.getCourseId() == null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        List<Notice> noticeList = noticeService.getNoticeList(notice);
        if (noticeList!=null){
            resultData.setResult(ResultCodeEnum.OK);
            resultData.setData(noticeList);
        } else {
            resultData.setResult(ResultCodeEnum.DB_FIND_FAILURE);
        }
        return resultData;
    }


    public ResultData addNotice(Notice notice) {
        ResultData<Notice> resultData = new ResultData <>();
        if (notice.getCourseId() == null || notice.getUserId() == null
                ||notice.getNoticeTitle() == null || notice.getNoticeContent() == null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        int result = noticeService.publishNotice(notice);
        if (result>0){
            resultData.setResult(ResultCodeEnum.OK);
        } else {
            resultData.setResult(ResultCodeEnum.DB_ADD_FAILURE);
        }
        return resultData;
    }

    public ResultData modifyNotice(Notice notice) {
        ResultData<Notice> resultData = new ResultData <>();
        if (notice.getNoticeId() == null || ( notice.getUserId() == null && notice.getCourseId() == null
                && notice.getNoticeTitle() == null && notice.getNoticeContent() == null)) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        int result = noticeService.modifyNotice(notice);
        if (result>0){
            resultData.setResult(ResultCodeEnum.OK);
        } else {
            resultData.setResult(ResultCodeEnum.DB_UPDATE_ERROR);
        }
        return resultData;
    }

    public ResultData deleteNotice(Notice notice) {
        ResultData<Notice> resultData = new ResultData <>();
        if (notice.getNoticeId() == null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        int result = noticeService.deleteNotice(notice);
        if (result>0){
            resultData.setResult(ResultCodeEnum.OK);
        } else {
            resultData.setResult(ResultCodeEnum.DB_DELETE_FAILURE);
        }
        return resultData;
    }
}
