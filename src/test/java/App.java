import com.wst.spider.PictureSpider;
import com.wst.util.PictureDownload;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        PictureSpider spider = ac.getBean(PictureSpider.class);

        String url="https://pic.sogou.com/pics?query=壁纸&mode=13&dm=4&cwidth=1920&cheight=1080";
        String path="D:/imgs";
        spider.downLoadAllPicture(url,path);

    }
}
