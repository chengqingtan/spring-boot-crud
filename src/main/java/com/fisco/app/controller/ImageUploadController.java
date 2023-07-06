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
import java.util.UUID;

@CrossOrigin
@RestController
public class ImageUploadController {

    SimpleDateFormat sdf = new SimpleDateFormat("/yyyy.MM.dd/");

    @Value("${image-save-path}")
    private String imageSavePath;

    @PostMapping("/upload_image")
    public ResponseData fileUpload(MultipartFile file, HttpServletRequest req){

        //获取文件全名(含拓展名)
        String originName = file.getOriginalFilename();
        //获取文件名不含后缀
        String fileName = originName.substring(0,file.getOriginalFilename().lastIndexOf("."));
        //获取文件后缀名(如.jpg .png)
        String fileSuffix = originName.substring(file.getOriginalFilename().lastIndexOf("."));
        if (".jpg".equals(fileSuffix) || ".png".equals(fileSuffix)) {
            //如果以 .jpg 或 .png 结尾
            //给上传的文件新建目录，目录名命名为当前日期，格式为 yyyy.MM.dd
            String format = sdf.format(new Date());
            String realPath = imageSavePath + format;
            System.out.println("realPath:"+realPath);
            //若目录不存在则创建目录
            File folder = new File(realPath);
            if(!folder.exists()) {
                folder.mkdirs();
            }
            //给上传文件取新的名字，避免重名，随机产生UUID作为文件名
            String newName = UUID.randomUUID().toString() + fileSuffix;
            try {
                //生成文件，folder为文件目录，newName为文件名
                file.transferTo(new File(folder,newName));
                //生成返回给前端的url
                String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() +"/images"+ format + newName;
                System.out.println("url:"+url);
                //返回URL
                return ResponseData.success(url);
            }catch (IOException e) {
                return ResponseData.error("图片上传失败");
            }
        }
        else {
            //如果不是以 .jpg 或 .png 结尾，不是图片
            return ResponseData.error("文件类型错误，请上传png或jpg!");
        }
    }
}
