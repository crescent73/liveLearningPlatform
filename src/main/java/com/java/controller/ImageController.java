package com.java.controller;

import com.java.constant.enums.ResultCodeEnum;
import com.java.model.entity.Course;
import com.java.model.entity.CourseFile;
import com.java.model.vo.ResultData;
import com.java.service.intf.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

@RestController
public class ImageController {
    @Autowired
    private CourseService courseService;

    @RequestMapping(value = "/image",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImage() throws IOException {
        try (InputStream is = new FileInputStream("in\\01.jpg")){
            byte[] bytes = new byte[is.available()];
            is.read(bytes,0,is.available());
            return bytes;
        }
    }


}
