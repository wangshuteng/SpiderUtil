package com.wst.util;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class HttpUtil {


    public String get(String path) {
        //创建连接客户端
        Request request = new Request.Builder()
                .url(path)
                .build();
        //创建"调用" 对象
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();

        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();//执行
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            System.out.println("链接格式有误:" + path);
        }
        return null;
    }


}
