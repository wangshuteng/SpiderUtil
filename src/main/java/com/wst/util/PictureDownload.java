package com.wst.util;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * 通过图片url 下载到指定路径
 */
public class PictureDownload implements Runnable{

    private String url;
    private String path;
    private String name;

    public PictureDownload(String url, String path) {
        this.url = url;
        this.path = path;
    }

    //检测路径是否存在，不存在则创建
    private void createFolder(String path)
    {
        File myPath = new File(path);
        if (!myPath.exists())   //不存在则创建文件夹
            myPath.mkdirs();
    }


    public void downLoad(){
        //创建文件夹
        createFolder(this.path);
        try {
            URL url = new URL(this.url);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            urlConnection.connect();

            String filePath = this.path+"/"+System.currentTimeMillis()+".png";

            BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
            int len=0;
            byte[] buff = new byte[1024];
            while ((len=inputStream.read(buff))!=-1){
                outputStream.write(buff,0,len);
            }
            outputStream.flush();

            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        downLoad();
    }
}
