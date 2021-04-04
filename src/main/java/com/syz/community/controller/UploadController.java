package com.syz.community.controller;

import com.syz.community.exception.CustomizeErrorCode;
import com.syz.community.exception.CustomizeException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class UploadController {
    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> imgResolver(@RequestParam("file") MultipartFile file) {
        String fileStr = file.getOriginalFilename();
        String newFileStr = UUID.randomUUID().toString() + fileStr.substring(fileStr.lastIndexOf("."));
        File descImg = makefile("D:\\workspace\\idea\\community\\src\\main\\resources\\static\\images\\" + newFileStr);
        Map<String, Object> map = new HashMap<>();
        try {
            file.transferTo(descImg);
        } catch (IOException e) {
            map.put("error", CustomizeErrorCode.IMAGE_UPLOAD_FAIL.getMessage());
        }
        map.put("fileName", newFileStr);//存储文件名
        return map;
    }

    private File makefile(String path) {
        if (path == null || "".equals(path.trim()))
            return null;
        String dirPath = path.substring(0, path.lastIndexOf("\\"));
        File dir = new File(dirPath);
        if (!dir.exists()) { //先建目录
            dir.mkdirs();
            dir = null;
        }
        //直接建目录
        int index = path.lastIndexOf(".");
        if (index > 0) { // 全路径，保存文件后缀
            File file = new File(path);
            if (!file.exists()) {//再建文件
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    throw new CustomizeException(CustomizeErrorCode.IMAGE_UPLOAD_FAIL);
                }
            }
            return file;
        } else {
            return dir;
        }
    }

    @RequestMapping(value = "/delPathFile", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> DelFiles(@RequestParam(value = "imgSrc", required = false) String fileName) {
        String imageName = fileName.substring(fileName.lastIndexOf("/"));
        File f = new File("D:\\workspace\\idea\\community\\src\\main\\resources\\static\\images\\" + imageName);
        Map<String, Object> map = new HashMap<>();
        try {
            DeleteFolder(f.getCanonicalPath());
        } catch (IOException e) {
            map.put("error", CustomizeErrorCode.IMAGE_DELETE_FAIL.getMessage());
        }
        map.put("message", imageName);//被删除的文件名
        return map;
    }

    private @ResponseBody
    static boolean DeleteFolder(String canonicalPath) {
        boolean flag = false;
        File file = new File(canonicalPath);
        // 判断目录或文件是否存在
        if (file.exists()) {
            if (file.isFile()) {
                return deleteFile(canonicalPath);
            } else {
                return flag;
            }
        }
        return flag;
    }

    public @ResponseBody
    static boolean deleteFile(String canonicalPath) {
        boolean flag = false;
        File file = new File(canonicalPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
}


