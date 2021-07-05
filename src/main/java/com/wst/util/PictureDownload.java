package com.wst.util;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 通过图片url 下载到指定路径
 */
public class PictureDownload implements Runnable{

//https://img03.sogoucdn.com/app/a/100520020/edce51b6fded8316f4568634a948bb71
    private String url;
    private String path;

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

            String filePath = this.path+"/"+System.currentTimeMillis()+".txt";

            BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
            int len=0;
            byte[] buff = new byte[1024];
            while ((inputStream.read(buff))!=-1){
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
