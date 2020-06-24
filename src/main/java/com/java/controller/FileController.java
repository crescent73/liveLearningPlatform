package com.java.controller;

import com.java.constant.enums.FileStorage;
import com.java.constant.enums.ResultCodeEnum;
import com.java.model.entity.CourseFile;
import com.java.model.vo.ResultData;
import com.java.service.intf.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Component
public class FileController {
    @Autowired
    private FileService fileService;

    public ResultData addFile(CourseFile courseFile, List<MultipartFile> files, HttpServletRequest req) {
        ResultData resultData = new ResultData <>();
        int result;
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(courseFile);
        System.out.println(files);
        if (courseFile.getCourseId()==null || courseFile.getUserId()==null || courseFile.getFileTitle() == null || files==null||files.size() <=0) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        for (MultipartFile file : files) {
            courseFile.setFileName(file.getOriginalFilename());
            String dirPath = req.getServletContext().getRealPath(FileStorage.FILE_STORAGE_PATH);
//            System.out.println(dirPath);
            courseFile.setFilePath(dirPath);
//            System.out.println(courseFile);
            result = fileService.uploadFile(courseFile,file);
            if (result <0 ) {
                resultData.setResult(ResultCodeEnum.FILE_UPLOAD_FAILURE);
            }
        }
        resultData.setResult(ResultCodeEnum.FILE_UPLOAD_SUCCESS);
        return resultData;
    }

    public ResultData searchFile(CourseFile file) {
        ResultData<List<CourseFile>> resultData = new ResultData <>();
        List<CourseFile> fileList = fileService.getFileList(file);
        if (fileList.size()>0) {
            resultData.setResult(ResultCodeEnum.OK);
            resultData.setData(fileList);
        } else {
            resultData.setResult(ResultCodeEnum.DB_FIND_FAILURE);
        }
        return resultData;
    }

    public ResponseEntity downloadFile(Integer fileId) throws UnsupportedEncodingException {
        System.out.println(fileId);
        ResultData<CourseFile> resultData = new ResultData <>();
        if (fileId == null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return ResponseEntity.ok().body(resultData);
        }
        File downloadFile = fileService.downloadFile(fileId);
        if (downloadFile == null) {
            resultData.setResult(ResultCodeEnum.FILE_EMPTY); //文件下载失败
            return ResponseEntity.ok().body(resultData);
        }
        HttpHeaders headers = new HttpHeaders();
        InputStreamResource resource;
        try {
            resource = new InputStreamResource(new FileInputStream(downloadFile));
        } catch (IOException e) {
            e.printStackTrace();
            resultData.setResult(ResultCodeEnum.FILE_DOWNLOAD_FAILURE); //文件下载失败
            return ResponseEntity.ok().body(resultData);
        }
        CourseFile file = new CourseFile();
        file.setFileId(fileId);
        List<CourseFile> fileList = fileService.getFileList(file);
        CourseFile courseFile = fileList.get(0);
        String encodedFileName = URLEncoder.encode(courseFile.getFileName(),"utf-8");
//        System.out.println(encodedFileName);
        headers.setContentDispositionFormData("file",encodedFileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    public ResultData modifyFileInfo(CourseFile file) {
        ResultData resultData = new ResultData <>();
        if (file.getFileId() == null || (file.getFileTitle()== null && file.getFileContent() == null)) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        int result = fileService.modifyFileInfo(file);
        if (result>0) {
            resultData.setResult(ResultCodeEnum.OK);
        } else {
            resultData.setResult(ResultCodeEnum.DB_UPDATE_ERROR);
        }
        return resultData;
    }

    public ResultData deleteFile(Integer fileId) {
        ResultData resultData = new ResultData <>();
        System.out.println(fileId);
        if (fileId == null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        int result = fileService.deleteFile(fileId);
        if(result>0) {
            resultData.setResult(ResultCodeEnum.OK);
        } else if (result == -1) {
            resultData.setResult(ResultCodeEnum.FILE_EMPTY);
        } else {
            resultData.setResult(ResultCodeEnum.FILE_DELETE_FAILURE);
        }
        return resultData;
    }
}
