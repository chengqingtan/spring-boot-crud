package com.fisco.app.controller;

import com.fisco.app.entity.ResponseData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RestController
public class ImageUploadController {

    SimpleDateFormat sdf = new SimpleDateFormat("/yyyy.MM.dd/");

    @Value("${image-save-path}")
    private String imageSavePath;

    @PostMapping("/upload_image")
    public ResponseData fileupload(MultipartFile file, HttpServletRequest req){

        //获取文件的名字
        String originName = file.getOriginalFilename();
        System.out.println("originName:"+originName);
        //判断文件类型
        if(!originName.endsWith(".jpg")) {
            return ResponseData.error("文件类型错误，请上传png或jpg");
        }
        //给上传的文件新建目录
        String format = sdf.format(new Date());
        String realPath = imageSavePath + format;
        System.out.println("realPath:"+realPath);
        //若目录不存在则创建目录
        File folder = new File(realPath);
        if(! folder.exists()) {
            folder.mkdirs();
        }
        //给上传文件取新的名字，避免重名
        String newName = UUID.randomUUID().toString() + ".jpg";
        try {
            //生成文件，folder为文件目录，newName为文件名
            file.transferTo(new File(folder,newName));
            //生成返回给前端的url
            String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() +"/images"+ format + newName;
            System.out.println("url:"+url);
            //返回URL
            return ResponseData.success(url);
        }catch (IOException e) {
            // TODO Auto-generated catch block
            return ResponseData.error("图片上传失败");
        }

    }
}
