package com.jin.core;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created by wuxia on 2018/5/16.
 */
@Component
@PropertySource("classpath:config.properties")
public class ConfigService implements InitializingBean {

    @Value("${file.roomPicPath}")
    public String roomPicPath;

    public String getRoomPicPath() {
        return roomPicPath;
    }

    public void setRoomPicPath(String roomPicPath) {
        this.roomPicPath = roomPicPath;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //没有文件就创建文件
        File file=new File(roomPicPath);
        if (!file.exists())file.mkdirs();
    }
}
