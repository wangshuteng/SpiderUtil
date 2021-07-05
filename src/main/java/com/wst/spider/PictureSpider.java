package com.wst.spider;


import com.wst.util.HttpUtil;
import com.wst.util.PictureDownload;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 下载给定连接中所有图片保存到本地
 * @author wangst
 * @date 2021/7/5
 */
public class PictureSpider {

    /**
     * 输入指定地址 url 保存到地址
     * @author wangst
     * @date 2021/7/5
     */
    public void downLoadAllPicture(String url,String path){

        List<String> imgLists = getAllPictureUrl(url);

        for (String link :
                imgLists) {
            new PictureDownload(link,path).downLoad();
        }

    }
    /**
     * 获取网址中包含的所有图片链接地址 返回list
     * @author wangst
     * @date 2021/7/5
     */
    public static List<String> getAllPictureUrl(String url){
        Document doc =null;
        List<String> lists = new ArrayList<>();
        try {
            doc = Jsoup.parse(HttpUtil.get(url),"utf-8");
        }catch (Exception e){
            System.out.println("获取网页连接错误！！");
            e.printStackTrace();
            return null;
        }
        Elements imgLinks=doc.select("img[src]");
        //遍历链接并处理链接地址
        for (Element e :  imgLinks) {
            String link = e.attr("src");
            //只能识别完整图片地址
            if(link.contains("http:")||link.contains("https:")) {

                lists.add(link);
            }
        }

        return lists;

    }




}
