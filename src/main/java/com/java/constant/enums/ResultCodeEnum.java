package com.java.constant.enums;


public enum ResultCodeEnum
{
	
	SITES_OPEN("101","网页打开成功"),
	INTERNTE_FAILURE("102","网络错误，请重试"),
	UNKOWN_ERROE("103","未知的错误"),
	REQUEST_NO_PARAM_ID_ERROR("104","页面请求参数错误"),
	DB_SYS_ERROR("105","数据库错误"),
	SERVER_ERROR("500","服务器内部错误"),
	
	//连接
	OK("200","OK"),
	//增
	DB_ADD_FAILURE("203","添加失败"),
	DB_ADD_TEACHER_NOT_EXIST("204","教师不存在"),
	DB_SIGN_FAILURE("204","教师不存在"),
	//删
	DB_DELETE_FAILURE("210","签到失败，请重试"),
	//改
	DB_UPDATE_ERROR("207","修改失败"),
	//查
	DB_FIND_FAILURE("209","查找失败，没有该条记录"),
	
	//请求参数	
	PARA_WORNING_NULL("301","必要请求参数为空"),
	PARA_FORMAT_ERROR("302","请求的参数格式错误"),
	PARA_NUM_ERROR("303","请求的参数个数错误"),
	TEACHER_NOT_EXIST("304","教师不存在"),
	STUDENT_NOT_EXIST("305","学生不存在"),
	SIGN_NOT_EXIST("305","签到不存在"),
	COURSE_NOT_EXIST("306","课程不存在"),
	COURSE_SCHEDULE_NOT_EXIST("306","节次不存在"),

	//系统功能
	LOGIN_SUCCESS("400","登录成功"),
	LOGIN_ERROR("401","登录失败_账号或密码错误"),
	NO_EXIST_USER("402","用户不存在"),
	NO_ENOUGH_MES("403","登录失败_账号或密码为空"),
	LOGOUT_SUCCESS("404","退出登录成功"),
	NO_LOGIN_USER("405","退出登录失败_用户未登录"),
	FILE_UPLOAD_SUCCESS("406","文件上传成功"),
	FILE_UPLOAD_FAILURE("407","文件上传失败"),
	FILE_UPLOAD_EMPTY("407","上传附件为空"),
	ATTACHMENT_UPLOAD_SUCCESS("406","附件上传成功"),
	ATTACHMENT_UPLOAD_FAILURE("407","附件上传失败"),
	ATTACHMENT_DOWNLOAD_SUCCESS("408","附件下载成功"),
	ATTACHMENT_DOWNLOAD_FAILURE("409","附件下载失败"),
	FILE_EMPTY("410","附件不存在"),
	USER_ALREADY_LOGIN("411","用户已登陆"),;
	
	private String code;
    private String desc;

    ResultCodeEnum(String code, String desc)
    {
        this.code = code;
        this.desc = desc;
    }

    public String getCode()
    {
        return code;
    }

    public String getDesc()
    {
        return desc;
    }


}
