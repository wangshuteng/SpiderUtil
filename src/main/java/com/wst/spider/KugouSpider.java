package com.wst.spider;

import com.wst.util.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


public class KugouSpider {
    private String url;

    @Autowired
    private HttpUtil httpUtil;

    public List<String> getMusicUrl(String url){
        List<String> list = new ArrayList<>();
        Document doc = null;
        try{
            doc= Jsoup.parse(httpUtil.get(url));
        }catch (Exception e){
            System.out.println("获取网页连接错误！！");
            e.printStackTrace();
            return null;
        }
        Elements musicLinks=doc.select("audio[src]");
        for (Element e :
                musicLinks) {
            String link = e.attr("src");
            if(link.contains("http:")||link.contains("https:")) {
                list.add(link);
            }
        }
        return list;

    }



}
