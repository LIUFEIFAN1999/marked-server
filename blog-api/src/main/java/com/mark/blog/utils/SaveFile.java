package com.mark.blog.utils;

import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class SaveFile {
    public static String string2md(final String filename, final String content) {
            String staticPath = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
            String url_path = "/markdown/"+ randomName(filename);
            String savePath = staticPath + url_path;
            File fileText = new File(savePath);
        try {
            FileWriter fileWriter = new FileWriter(fileText);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url_path;
    }

    public static String randomName(String fileName){
        int index = fileName.lastIndexOf(".");
        String suffix = fileName.substring(index);//获取后缀名
        String uuidFileName= UUID.randomUUID().toString().replace("-","")+suffix;
        return uuidFileName;
    }

    public static String saveImg(MultipartFile file) {
        String staticPath = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
        //获取文件名
        String fileName = file.getOriginalFilename();
        // 图片存储目录及图片名称
        String url_path = "/images/"+ randomName(fileName);
        //图片保存路径
        String savePath = staticPath + url_path;
        File saveFile = new File(savePath);
        if (!saveFile.exists()){
            saveFile.mkdirs();
        }
        //将临时存储的文件移动到真实存储路径下
        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url_path;
    }
}
