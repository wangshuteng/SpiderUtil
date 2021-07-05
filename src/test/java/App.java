import com.wst.util.PictureDownload;

public class App {
    public static void main(String[] args) {
        String url="https://img03.sogoucdn.com/app/a/100520020/edce51b6fded8316f4568634a948bb71";
        String path="D:/imgs";
        PictureDownload pictureDownload = new PictureDownload(url,path);

        pictureDownload.downLoad();

    }
}
