package com.wst.spider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wst.util.FileDownload;
import com.wst.util.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class KugouSpider {
    private String url;

    public HttpUtil httpUtil = new HttpUtil();

    public static void main(String[] args) {
        String url = "https://www.kugou.com/yy/rank/home/1-6666.html?from=rank";
        Document doc = null;
        HttpUtil httpUtil = new HttpUtil();
        try{
            doc= Jsoup.parse(httpUtil.get(url));
        }catch (Exception e){
            System.out.println("获取网页连接错误！！");
            e.printStackTrace();
        }
        Elements pc_temp_songname = doc.getElementsByClass("pc_temp_songname");

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        String path = "D:\\mus";
        for (Element e : pc_temp_songname) {
            String link = e.attr("href");
            String title = e.attr("title");
            String musicUrl = getMusicUrl(link);
            musicUrl = musicUrl.replace("\\","");
            title = title +".mp3";

            System.out.println(title+":"+musicUrl);
            executorService.submit(new FileDownload(musicUrl,path,title));
        }
    }


    public void downLoadMusic(){
        String url = "https://www.kugou.com/yy/rank/home/1-6666.html?from=rank";
        Document doc = null;
        try{
            doc= Jsoup.parse(httpUtil.get(url));
        }catch (Exception e){
            System.out.println("获取网页连接错误！！");
            e.printStackTrace();
        }
        Elements pc_temp_songname = doc.getElementsByClass("pc_temp_songname");

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        String path = "D:/mus";
        for (Element e : pc_temp_songname) {
           String link = e.attr("href");
           String title = e.attr("title");
            String musicUrl = getMusicUrl(link);

            executorService.submit(new FileDownload(musicUrl,title,path));
        }
        executorService.shutdown();


    }

    /**
     * 获取完整音乐地址的url
     * @author wangst
     * @date 2021/7/7
     * @return  * @param url
     */
    public static String getMusicUrl(String url){
        Map<String, String> hashAndId = getHashAndId(url);
        HttpUtil httpUtil = new HttpUtil();
        String musicUrl="";
        if (!hashAndId.isEmpty()){
            String hash = hashAndId.get("hash");
            String album_id = hashAndId.get("album_id");
            String codeUrl="https://wwwapi.kugou.com/yy/index.php?" +
                    "r=play/getdata" +
                    "&callback=jQuery19103260561307019262_1625668049988" +
                    "&hash=" +hash+
                    "&dfid=2T62ov0BDeNu3KFmwZ32R25O" +
                    "&mid=93cb60a3d4e8c16e8732c422483fbded" +
                    "&platid=4" +
                    "&album_id=" +album_id +
                    "&_=1625668049990";
            String mstr = httpUtil.get(codeUrl);
            String regEx = "https:([\\S]*?).mp3";
            // 编译正则表达式
            Pattern pattern = Pattern.compile(regEx);
            Matcher matcher = pattern.matcher(mstr);
            if (matcher.find()) {
                musicUrl = matcher.group();
            }
        }
        return musicUrl;
    }

    /**
     * 获取连接中包含的hash 和 album_id 参数
     * @author wangst
     * @date 2021/7/7
     * @return  * @param url
     */
    public static Map<String,String> getHashAndId(String url){
        Map<String,String> map = new HashMap<>();
        HttpUtil httpUtil = new HttpUtil();
        Document doc = null;
        try{
            doc= Jsoup.parse(httpUtil.get(url));
        }catch (Exception e){
            System.out.println("获取网页连接错误！！");
            e.printStackTrace();
        }

        Elements script = doc.getElementsByTag("script");

        for (Element e : script) {
            String[] vars = e.data().split("var");
            for (String var : vars) {
                if(var.contains("dataFromSmarty")){
                    String dataFromSmarty=var.substring(var.indexOf("[")+1,var.indexOf("]"));
                    JSONObject parse = JSON.parseObject(dataFromSmarty);
                    String hash = parse.getString("hash");
                    String album_id = parse.getString("album_id");
                    map.put("hash",hash);
                    map.put("album_id",album_id);

                }
            }
        }
        return map;
    }



}
