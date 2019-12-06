package com.wyc.startup;

import com.wyc.core.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by wangyc on 2019/11/18.
 */
@Component
public class StartUpTask {


    private static String uploadFileDir;

    @Value("${filesDir.uploadFileDir}")
    public void setUploadFileDir(String uploadFileDir) {
        this.uploadFileDir = uploadFileDir;
    }

    /**
     * 创建目录
     */
    @PostConstruct
    public void makeDir(){
        FileUtil.touchDir(uploadFileDir);
        System.out.println("创建目录"+uploadFileDir);
    }
}
