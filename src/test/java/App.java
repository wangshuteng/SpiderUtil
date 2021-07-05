import com.wst.spider.PictureSpider;
import com.wst.util.PictureDownload;

public class App {
    public static void main(String[] args) {
        String url="https://pic.sogou.com/pics?query=壁纸&mode=13&dm=4&cwidth=1920&cheight=1080";
        String path="D:/imgs";
//        PictureDownload pictureDownload = new PictureDownload(url,path);
//
//        pictureDownload.downLoad();

        PictureSpider spider = new PictureSpider();
        spider.downLoadAllPicture(url,path);

    }
}
