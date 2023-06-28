package com.azha.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
@RestController
public class FileUploadController {
    @PostMapping("/upload")
    public String up(String name, MultipartFile file, HttpServletRequest request) throws IOException {
        System.out.println(name);
        System.out.println(file.getOriginalFilename());
//        System.out.println(System.getProperty("user.dir"));
        String path = request.getServletContext().getRealPath("/upload/");//获取运行目录
        System.out.println(path);
        saveFile(file,path);
        return "上传结束";
    }

    public void saveFile(MultipartFile file,String path)throws IOException{
        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdir();
        }
        File file_upload=new File(path+file.getOriginalFilename());
        file.transferTo(file_upload);
    }
}
