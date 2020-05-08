package com.java.controller;

import com.java.constant.enums.ResultCodeEnum;
import com.java.model.entity.*;
import com.java.model.vo.ResultData;
import com.java.service.intf.LiveUserService;
import com.java.service.intf.SystemService;
import com.java.utils.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private LiveUserService liveUserService;

    private ResultData resultData;
    @Autowired
    private LiveController liveController;

    @Autowired
    private SystemService systemService;

    @RequestMapping("/register")
    public ResultData register(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException
     {
        User user = new User();
        //获取前台JSP页面参数
        String nickName = request.getParameter("userNickName");
        String password = request.getParameter("userPassword");
        String gender = request.getParameter("userGender");
        String email = request.getParameter("userEmail");
        String role = request.getParameter("userRole");
        int schoolID = Integer.parseInt(request.getParameter("schoolID"));
        String number = request.getParameter("userNumber");
        String name = request.getParameter("userName");

        user.setUserNickname(nickName);
        user.setUserPassword(password) ;
        user.setUserGender(gender) ;
        user.setUserEmail(email);
        user.setUserRole(role) ;
        user.setSchoolId(schoolID);
        user.setUserNumber(number) ;
        user.setUserName(name) ;

         //随机生成用户ID（不重复4位随机数）
         Calendar c=Calendar.getInstance();
         String time=new SimpleDateFormat("yyyy-MM-ddHHmmss").format(c.getTime()).toString();
         StringBuffer s=new StringBuffer(time.substring(14, 16));
         Long sys=System.currentTimeMillis();
         s.append(sys.toString().substring(11, 13));
         Double tm=Math.random()*10000+1;
         s.append(tm.toString().substring(tm.toString().length()-10, tm.toString().length()));
         user.setUserId(Integer.valueOf(String.valueOf(s)).intValue());

        if(!StringUtils.isEmpty(user.getUserName())&& !StringUtils.isEmpty(user.getUserPassword())
                && !StringUtils.isEmpty(user.getUserNickname())){
                resultData = new ResultData();
                resultData = systemService.register(user);
                if(resultData !=null){
                    response.setHeader("refresh","1;url=UserServlet?method=talon");
                }else{
                    request.setAttribute("msg", "注册失败！");
                    request.getRequestDispatcher("/message").forward(request,response);
                }

            }else{
            request.setAttribute("msg", "用户名、密码或者姓名不许为空！");
            request.getRequestDispatcher("/message").forward(request,
                    response);
        }
         return resultData;
    }

    @RequestMapping("/login")
    public ResultData login(String name, String password, String userType, HttpServletRequest request) {
        System.out.println(name+"-"+password+"-"+userType);
        resultData = new ResultData();
        User user = new User();
        user.setUserNickname(name);
        user.setUserPassword(password);
        user.setUserRole(userType);
        user = systemService.login(user);
        if (user!=null){
            HttpSession session = request.getSession();
            LiveUser liveUser = new LiveUser();
            liveUser.setIp(IpUtil.getIp(request));
            liveUser.setRandomName(user.getUserNickname());
            liveUserService.insert(liveUser);
            session.setAttribute("user",liveUser);
            resultData.setResult(ResultCodeEnum.OK);
            resultData.setData(user);
        } else {
            resultData.setResult(ResultCodeEnum.LOGIN_ERROR);
        }

        return resultData;
    }

    @RequestMapping("/logout")
    public ResultData logout(Long id, String userType, HttpSession session){
        if(id!=null){//判断是否为空字符串
            resultData = new ResultData();
            if(session.getAttribute("login")==null){//获取属性
                resultData.setResult(ResultCodeEnum.NO_LOGIN_USER);
            }
            else if(session.getAttribute("login").equals(1)){
                session.removeAttribute("login");
                session.removeAttribute("user");
                resultData.setResult(ResultCodeEnum.LOGOUT_SUCCESS);//成功退出
            }
            else{
                resultData.setResult(ResultCodeEnum.UNKOWN_ERROE);
            }
            resultData = systemService.logout(id, userType);
        }else{
            resultData = new ResultData();
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
        }
        return resultData;
    }

    @RequestMapping("/modifyInfo")
    public ResultData modifyInfo(Integer id, String password, String userType, HttpSession session) {
        if(id != null ) {
            try{
                resultData = systemService.modifyInfo(id, password, userType, session);
            }catch(Exception e){
                e.printStackTrace();
                resultData = new ResultData();
                resultData.setResult(ResultCodeEnum.SERVER_ERROR);
            }

        } else {
            resultData = new ResultData();
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
        }
        return resultData;
    }



    @RequestMapping("/connect")
    public ResultData connect(HttpServletRequest request){
        String ip = IpUtil.getIp(request);
        HttpSession session = request.getSession();
        LiveUser user = (LiveUser) session.getAttribute("user");
        if(user != null) {
            System.out.println("已登录");
        } else {
            System.out.println("用户未登录！");
            user = new LiveUser();
            user.setIp(ip);
            user.setRandomName("guest");
            liveUserService.insert(user);
            session.setAttribute("user",user);
        }
        List allUserOnline = liveController.getOnlinePeople();
        List allGuestHistory = liveController.getHistoryGuest();
        Map<String,List> all = new HashMap<String,List>();
        all.put("allUserOnline",allUserOnline);
        all.put("allGuestHistory",allGuestHistory);
        resultData = new ResultData <List>();
        resultData.setResult(ResultCodeEnum.OK);
        resultData.setData(all);
        return resultData;
    }

    @RequestMapping("/getNoticeList")
    //教师学生公用
    public ResultData getNoticeList(Notice notice, PageParam pageParam) {
        if(notice != null && notice.getCourseId() != null) {
            System.out.println(pageParam);
            System.out.println(notice);
            try{
                resultData = systemService.getNoticeList(notice,pageParam);
            }catch(Exception e){
                e.printStackTrace();
                resultData = new ResultData();
                resultData.setResult(ResultCodeEnum.SERVER_ERROR);
            }
        }else {
            resultData = new ResultData();
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
        }
        return resultData;
    }

    @RequestMapping("/searchFile")
    @ResponseBody
    //教师学生公用
    public ResultData searchFile(File file, PageParam pageParam) {
        if(file != null && file.getCourseId() != null) {
            try{
                resultData = systemService.searchFile(file,pageParam);
            }catch(Exception e){
                e.printStackTrace();
                resultData = new ResultData();
                resultData.setResult(ResultCodeEnum.SERVER_ERROR);
            }
        }else {
            resultData = new ResultData();
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
        }
        System.out.println(resultData);
        return resultData;
    }

    @RequestMapping("/downloadAttachment")
    @ResponseBody
    //教师学生公用
    public ResponseEntity downloadAttachment(Integer attachmentId, @RequestBody Map<String,Object> map) throws UnsupportedEncodingException {
        if(attachmentId == null){
            attachmentId = (Integer) map.get("attachmentId");
        }
        System.out.println("attachmentId1"+attachmentId);
        System.out.println("attachmentId2"+map.get("attachmentId"));
        if (attachmentId != null) {
            Attachment attachment = new Attachment();
            attachment.setId(attachmentId);
            AttachmentDetail attachmentDetail = null;
            try{
                attachmentDetail = systemService.downloadAttachment(attachment);
            }catch(Exception e){
                e.printStackTrace();
                resultData = new ResultData();
                resultData.setResult(ResultCodeEnum.SERVER_ERROR);
            }
            System.out.println(attachmentDetail);
            if(attachmentDetail != null) {
                InputStreamResource resource;
                try {
                    resource = new InputStreamResource(new FileInputStream(String.valueOf(attachmentDetail.getFile())));
                } catch (IOException e) {
                    e.printStackTrace();
                    resultData = new ResultData();
                    resultData.setResult(ResultCodeEnum.ATTACHMENT_DOWNLOAD_FAILURE); //文件下载失败
                    return ResponseEntity.ok().body(resultData);
                }
                HttpHeaders headers = new HttpHeaders();
                String encodedFileName = URLEncoder.encode(attachmentDetail.getAttachmentName(),"utf-8");
                headers.setContentDispositionFormData("attachment",encodedFileName);
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                return ResponseEntity.ok().headers(headers).body(resource);
            } else {
                resultData = new ResultData();
                resultData.setResult(ResultCodeEnum.ATTACHMENT_DOWNLOAD_FAILURE); //文件下载失败
                return ResponseEntity.ok().body(resultData);
            }
        } else {
            resultData = new ResultData();
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL); //返回重要参数为空
            return ResponseEntity.ok().body(resultData);
        }
    }

    @RequestMapping("/getAssignmentList")
    @ResponseBody
    //教师学生公用，作业以文件的形式呈现
    public ResultData getAssignmentList(File file, PageParam pageParam){
        if(file != null && file.getCourseId() != null) {
            try{
                resultData = systemService.searchFile(file,pageParam);
            }catch(Exception e){
                e.printStackTrace();
                resultData = new ResultData();
                resultData.setResult(ResultCodeEnum.SERVER_ERROR);
            }
        }else {
            resultData = new ResultData();
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
        }
        System.out.println(resultData);
        return resultData;
    }

    @RequestMapping("/getAssignmentDetail")
    @ResponseBody
    //获取作业详情（Student和Teacher公用），作业详情以下载的文件呈现
    public ResponseEntity getAssignmentDetail(Integer attachmentId, @RequestBody Map<String,Object> map) throws UnsupportedEncodingException {
        if(attachmentId == null){
            attachmentId = (Integer) map.get("attachmentId");
        }
        System.out.println("attachmentId1"+attachmentId);
        System.out.println("attachmentId2"+map.get("attachmentId"));
        if (attachmentId != null) {
            Attachment attachment = new Attachment();
            attachment.setId(attachmentId);
            AttachmentDetail attachmentDetail = null;
            try{
                attachmentDetail = systemService.downloadAttachment(attachment);
            }catch(Exception e){
                e.printStackTrace();
                resultData = new ResultData();
                resultData.setResult(ResultCodeEnum.SERVER_ERROR);
            }
            System.out.println(attachmentDetail);
            if(attachmentDetail != null) {
                InputStreamResource resource;
                try {
                    resource = new InputStreamResource(new FileInputStream(String.valueOf(attachmentDetail.getFile())));
                } catch (IOException e) {
                    e.printStackTrace();
                    resultData = new ResultData();
                    resultData.setResult(ResultCodeEnum.ATTACHMENT_DOWNLOAD_FAILURE); //文件下载失败
                    return ResponseEntity.ok().body(resultData);
                }
                HttpHeaders headers = new HttpHeaders();
                String encodedFileName = URLEncoder.encode(attachmentDetail.getAttachmentName(),"utf-8");
                headers.setContentDispositionFormData("attachment",encodedFileName);
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                return ResponseEntity.ok().headers(headers).body(resource);
            } else {
                resultData = new ResultData();
                resultData.setResult(ResultCodeEnum.ATTACHMENT_DOWNLOAD_FAILURE); //文件下载失败
                return ResponseEntity.ok().body(resultData);
            }
        } else {
            resultData = new ResultData();
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL); //返回重要参数为空
            return ResponseEntity.ok().body(resultData);
        }
    }

}
