package com.java.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.dao.NoticeDao;
import com.java.model.entity.Notice;
import com.java.service.intf.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeDao noticeDao;
    @Override
    public List<Notice> getNoticeList(Notice notice) {
        QueryWrapper<Notice> noticeQueryWrapper = new QueryWrapper<>();
        noticeQueryWrapper.eq(notice.getNoticeId()!=null,"notice_id",notice.getNoticeId());
        noticeQueryWrapper.eq("course_id",notice.getCourseId());
        noticeQueryWrapper.eq(notice.getUserId()!=null,"user_id",notice.getUserId());
        noticeQueryWrapper.eq(notice.getNoticeTitle()!=null,"notice_title",notice.getNoticeTitle());
        noticeQueryWrapper.eq(notice.getNoticeContent()!=null,"notice_content",notice.getNoticeContent());
        List<Notice> notices = noticeDao.selectList(noticeQueryWrapper);
        if (notices.size()>0) {
            return notices;
        } else {
            return null;
        }
    }

    @Override
    public int publishNotice(Notice notice) {
        int insert = noticeDao.insert(notice);
        return insert;
    }

    @Override
    public int modifyNotice(Notice notice) {
        int update = noticeDao.update(notice);
        return update;
    }

    @Override
    public int deleteNotice(Notice notice) {
        int result = noticeDao.deleteById(notice.getCourseId());
        return result;
    }
}
