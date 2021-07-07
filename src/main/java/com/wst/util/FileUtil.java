package com.wst.util;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FileUtil {

    /**
     * 根据传入路径创建文件夹
     * @param path
     */
    public void createFolder(String path)
    {
        File myPath = new File(path);
        if (!myPath.exists())   //不存在则创建文件夹
            myPath.mkdirs();
    }



}
