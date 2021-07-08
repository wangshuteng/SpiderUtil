import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wst.spider.KugouSpider;
import com.wst.spider.PictureSpider;
import com.wst.util.HttpUtil;
import com.wst.util.PictureDownload;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sun.security.timestamp.HttpTimestamper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {


    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        PictureSpider spider = ac.getBean(PictureSpider.class);

        String url="https://pic.sogou.com/pics?query=壁纸&mode=13&dm=4&cwidth=1920&cheight=1080";
        String path="D:/imgs";
        spider.downLoadAllPicture(url,path);

    }

    @Test
    public void fun1(){
        try {
            URL url = new URL("https://webfs.ali.kugou.com/202107071633/55a78c7e9975f0bf4862b48b3fde2f8b/G241/M0B/0B/05/MQ4DAF-uelmAauKlAD_SIdgGLsY233.mp3");
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            urlConnection.connect();

            String filePath = "D:/imgs/aa.mp3";

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

    @Test
    public void fun2(){
        List<String> list = new ArrayList<>();
        HttpUtil httpUtil = new HttpUtil();
        Document doc = null;
        String url ="https://www.kugou.com/song/1sxgnf41.html";
//        String url ="https://wwwapi.kugou.com/yy/index.php?r=play/getdata&callback=jQuery19103260561307019262_1625668049988&hash=D5E5641860B5BE111A9DF0ACD3AD7E3A&dfid=2T62ov0BDeNu3KFmwZ32R25O&mid=93cb60a3d4e8c16e8732c422483fbded&platid=4&album_id=45507535&_=1625668049990";
//        String mstr = httpUtil.get(url);
//        String hash = "";
//        String regEx = "https:([\\S]*?).mp3";
//        // 编译正则表达式
//        Pattern pattern = Pattern.compile(regEx);
//        Matcher matcher = pattern.matcher(mstr);
//        if (matcher.find()) {
//            hash = matcher.group();
//            System.out.println(hash);
//        }

//
//        String[] urls = s.split("play_backup_url");
//        for (String a :
//                urls) {
//            if(a.contains("mp3")) {
//                System.out.println(a);
//            }
//
//        }


        try{
            doc= Jsoup.parse(httpUtil.get(url));
        }catch (Exception e){
            System.out.println("获取网页连接错误！！");
            e.printStackTrace();
        }

        Elements script = doc.getElementsByTag("script");
        doc.getElementsByClass("");

        for (Element e : script) {
            String[] vars = e.data().split("var");
            for (String var :
                    vars) {
                if(var.contains("dataFromSmarty")){
                    String dataFromSmarty=var.substring(var.indexOf("[")+1,var.indexOf("]"));
                    System.out.println(dataFromSmarty);
                    JSONObject parse = JSON.parseObject(dataFromSmarty);
                    String hash = parse.getString("hash");
                    String album_id = parse.getString("album_id");

                }
            }

        }

    }
    @Test
    public void fun3(){
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
        for (Element e :
                pc_temp_songname) {
            String link = e.attr("href");
            String title = e.attr("title");
            System.out.println(title+":"+link);
        }
    }
    @Test
    public void fun4(){
        String url = "https://www.kugou.com/yy/rank/home/1-6666.html?from=rank";
        KugouSpider kugouSpider = new KugouSpider();
        kugouSpider.downLoadMusic();

    }
    @Test
    public void fun5(){
        HttpUtil httpUtil = new HttpUtil();
        String keyWord = "雪";
        String url ="https://www.kugou.com/yy/html/search.html#searchType=song&searchKeyWord=雪"+keyWord;
        String url2="https://searchrecommend.kugou.com/get/complex?callback=jQuery112409968025656871942_1625729486178&word=雪&_=1625729486180";
        String url3="https://complexsearch.kugou.com/v2/search/song?callback=callback123&keyword=雪&page=1&pagesize=30&bitrate=0&isfuzzy=0&tag=em&inputtype=0&platform=WebFilter&userid=0&clientver=2000&iscorrection=1&privilege_filter=0&srcappid=2919&clienttime=1625732037821&mid=1625732037821&uuid=1625732037821&dfid=-&signature=4325B1FF2C9F63F221F62D2EF82D1E5C";
        String url4="https://complexsearch.kugou.com/v2/search/song?callback=callback123&keyword=雪&page=1&pagesize=30&bitrate=0&isfuzzy=0&tag=em&inputtype=0&platform=WebFilter&userid=0&clientver=2000&iscorrection=1&privilege_filter=0&srcappid=2919&clienttime=1625733278418&mid=1625733278418&uuid=1625733278418&dfid=-&signature=1AF03CCC0FF12D10C4BF3757AB1C9D05";

        System.out.println(url3);
        Document doc = Jsoup.parse(httpUtil.get(url3));
        System.out.println(doc);
    }
    @Test
    public void fun6(){
        HttpUtil httpUtil = new HttpUtil();
        String musicUrl="";
            String hash = "AA9CA20BE39C1310AB266B509788B8AC";
//            String album_id = "39932747";
            String album_id = "14462";
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
            System.out.println(mstr);
            String regEx = "https:([\\S]*?).mp3";
            // 编译正则表达式
            Pattern pattern = Pattern.compile(regEx);
            Matcher matcher = pattern.matcher(mstr);
            if (matcher.find()) {
                musicUrl = matcher.group();
                System.out.println(musicUrl);
            }
    }

}
