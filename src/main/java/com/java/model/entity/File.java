package com.java.model.entity;

import java.lang.reflect.Field;
import java.sql.Timestamp;

public class File {
    private Integer id;
    private String fileName;
    private Timestamp uploadDate;
    private Timestamp updateDate;
    private Integer courseId;
    private Integer uploaderId;
    private String fileDescription;
    private String assess;//作业评价


    public String getFileDescription() {
        return fileDescription;
    }

    public void setFileDescription(String fileDescription) {
        this.fileDescription = fileDescription;
    }

    public String getFileAssess() {
        return assess;
    }

    public void setFileAssess(String fileDescription) {
        this.assess = assess;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public Timestamp getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Timestamp uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(Integer uploaderId) {
        this.uploaderId = uploaderId;
    }

    public void reset() {
        Field[] fields = getClass().getDeclaredFields();
        for(Field field : fields) {
            try {
                field.set(this, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", uploadDate=" + uploadDate +
                ", updateDate=" + updateDate +
                ", courseId=" + courseId +
                ", uploaderId=" + uploaderId +
                ", fileDescription='" + fileDescription + '\'' +
                ",assess='"+assess+
                '}';
    }
}

