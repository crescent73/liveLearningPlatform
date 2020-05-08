package com.java.model.entity;

import java.lang.reflect.Field;
import java.sql.Timestamp;

public class Notice {
    private Integer id;
    private Integer courseId;
    private Integer publisherId;
    private Integer noticeLevel;
    private String noticeContent;
    private Timestamp publishDate;
    private Timestamp updateDate;
    private String noticeTitle;

    public void reset() {
        Field[] fields = getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.set(this, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public Integer getNoticeLevel() {
        return noticeLevel;
    }

    public void setNoticeLevel(Integer noticeLevel) {
        this.noticeLevel = noticeLevel;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public Timestamp getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Timestamp publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", publisherId=" + publisherId +
                ", noticeLevel=" + noticeLevel +
                ", noticeContent='" + noticeContent + '\'' +
                ", publishDate=" + publishDate +
                ", updateDate=" + updateDate +
                ", noticeTitle='" + noticeTitle + '\'' +
                '}';
    }
}
