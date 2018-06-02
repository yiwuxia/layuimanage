package com.jin.controller;

import com.jin.core.ConfigService;
import com.jin.domin.UploadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by wuxia on 2018/5/30.
 */
@Controller
@RequestMapping(value = "/upload")
public class UploadController {


    @Autowired
    ConfigService configService;


    @RequestMapping(value = "/uploadRoomPic")
    @ResponseBody
    public UploadResult uploadUserPic(
            MultipartFile file,
            HttpSession session,
            String id,
            HttpServletRequest request
    ){

        /* String classpath = this.getClass().getResource("/").getPath().replaceFirst("/", "");
        String docRoot = classpath.replaceAll("WEB-INF/classes/", "upload");
        String filePath=docRoot+ "/" + "jin";*/
        //根据id参数 确定图片名称，保存 返回。
        String realName = "";
        if (file != null) {
            String fileName = file.getOriginalFilename();
            String fileNameExtension = fileName.substring(fileName.indexOf("."));
            // 生成实际存储的真实文件名

            realName = "room"+id + fileNameExtension;

            // "/upload"是你自己定义的上传目录
          //  String classpath = this.getClass().getResource("/").getPath().replaceFirst("/", "");
            String realPath =configService.getRoomPicPath() ;//classpath.replaceAll("WEB-INF/classes/", "roomImg");
            File uploadFile = new File(realPath, realName);
            try {
                file.transferTo(uploadFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String  str = request.getContextPath() + "/roompic/" + realName; ///roompic
       // List<String> list =new ArrayList<String>();
        UploadResult result=UploadResult.returnSuccessResult();
        result.setData(Arrays.asList(str));
        return result;
    }
}
