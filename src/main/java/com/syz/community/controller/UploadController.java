package com.syz.community.controller;

import com.syz.community.dto.ResultDTO;
import com.syz.community.exception.CustomizeErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
public class UploadController {

    @Value("${SUMMERNOTE_FILE_UPLOAD}")
    private String SUMMERNOTE_FILE_UPLOAD;

    @RequestMapping(value="/upload/img",method={RequestMethod.POST})
    @ResponseBody
    public Object uploadSummerNoteImgs(@RequestParam("file") MultipartFile file, Model model) {
        //文件保存路径，自定义路径
        String savePath=SUMMERNOTE_FILE_UPLOAD;
        //保存文件，返回文件访问地址
        return savePngFile(file, savePath);
    }
    public static Object savePngFile(MultipartFile file, String path) {
        //判断文件是否为空
        if (!file.isEmpty()) {
            try {
                //创建每天生成的目录
                String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
                path=path+date+"/";
                File filepath = new File(path);
                if (!filepath.exists()){
                    filepath.mkdirs();
                }
                //文件保存路径
                //String filename = file.getOriginalFilename();
                //重新生成文件名
                String filename= UUID.randomUUID().toString().replace("-", "")+".png";
                String savePath = path + filename;
                //转存文件
                File uploadFile = new File(savePath);
                file.transferTo(uploadFile);
                return date+"/"+filename;
            } catch (Exception e) {
                return ResultDTO.errorOf(CustomizeErrorCode.IMAGE_UPLOAD_FAIL.getCode(), CustomizeErrorCode.IMAGE_UPLOAD_FAIL.getMessage());
            }
        }
        return null;
    }
}


