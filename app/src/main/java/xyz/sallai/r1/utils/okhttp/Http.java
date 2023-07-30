package xyz.sallai.r1.utils.okhttp;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

public class Http {


    private String TAG = this.getClass().getSimpleName();

    public String sendHttp(String url){

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36")
                .build();

        try (Response response= client.newCall(request).execute()) {

            return response.body().string();  //解析成小讯格式

        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("okHttp","okhttp->error");
        return "ERROR";

    }


    public static String getHttpCookie(String url){

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36")
                .build();

        try (Response response= client.newCall(request).execute()) {
            Headers  headers=response.headers();
            //获取cookie
            List<String> cookies = headers.values("Set-Cookie");
            String s = cookies.toString();
            return s;

        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("okHttp","okhttp->error");
        return "ERROR";

    }

    public String sendPostHttp(String url, String data){
        RequestBody body = RequestBody.create(data,MediaType.parse("application/json"));
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36")
                .post(body)
                .build();

        try (Response response= client.newCall(request).execute()) {

            return response.body().string();  //解析成小讯格式

        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("okHttp","okhttp->error");
        return "ERROR";

    }


    public static boolean downloadSmallFile(final String uri, final String filePath) {
        String httpCookie = getHttpCookie(uri);
        httpCookie = httpCookie.replaceAll("\\[","").replaceAll("]", "");
        System.out.println(httpCookie);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(uri.toString())
                .addHeader("Cookie",httpCookie)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                System.out.println("响应失败");
                return false;
            }

            ResponseBody body = response.body();
            long contentLength = body.contentLength();
            System.out.println("响应长度"+contentLength);
            BufferedSource source = body.source();
            File file = new File(filePath);
            BufferedSink sink = Okio.buffer(Okio.sink(file));
            sink.writeAll(source);
            sink.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }



}