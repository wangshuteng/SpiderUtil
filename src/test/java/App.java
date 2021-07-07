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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

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
        String url ="https://www.kugou.com/song/#hash=0930C43952C442A194129D20F48182FC&album_id=40154991";
//        String url ="https://www.baidu.com/";
        try{
            doc= Jsoup.parse(httpUtil.get(url));
//            doc = Jsoup.parse(new URL(url),3000);
            System.out.println(doc);
        }catch (Exception e){
            System.out.println("获取网页连接错误！！");
            e.printStackTrace();
        }
        Elements musicLinks=doc.select("audio[src]");
        for (Element e :
                musicLinks) {
            String link = e.attr("src");
            if(link.contains("http:")||link.contains("https:")) {
                list.add(link);
                System.out.println(link);
            }
        }
    }

}
