package com.example.main.okhttp;

import android.app.Activity;
import android.widget.Toast;

import com.example.main.R;
import com.example.main.Utils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 网络请求用的是OKHttp，这个开源项目的好处有
 * 1.Android 6.0后不支持HttpClient请求，而它使用HttpUrlConnection
 * 2.默认支持https
 * 3.非常高效，支持SPDY(是谷歌开发的基于TCP的应用层协议,用于最小化网络延迟,提升网络速度,优化用户的网络使用体验. SPDY并不是一种替代http的协议,只是对http的一种增强),
 * 可访问https://baike.baidu.com/item/SPDY/3399551?fr=aladdin
 * 4.OKHttp会自动处理常见的网络问题，像自动重连，利用响应缓存来避免重复的网络请求.即便是网络出现问题时,okhttp依然起作用.它将从常见的链接问题当中回复.
 * 如果你的服务器有多个IP地址,当地一个失败时,okhttp会自动尝试连接其他的地址.
 * 这对于IPV4和IPV6以及寄宿在多个数据中心的服务而言,是非常有必要的,所以okhttp的稳定性可以说是非常棒的
 * 5。OkHttp还处理了代理服务器问题和SSL握手失败问题
 * 6.默认支持GZIP
 * HTTP缓存，就是浏览器和服务端之间保持长连接，这个连接是可以复用的
 * 7.从Android4.4开始HttpURLConnection的底层实现采用的是okHttp
 */
public class NetRequest {
    private CallBack.NetRequestIterface netRequestIterface;
    private Activity context;

    public NetRequest(CallBack.NetRequestIterface netRequestIterface, Activity context) {
        this.netRequestIterface = netRequestIterface;
        this.context = context;
    }

    public void httpRequest(Map<String, Object> map, final String requestUrl) {
        if (!Utils.getInstance().isConnectingToInternet(context)) {
            Toast.makeText(context,
                    context.getString(R.string.internet_fail_connect), Toast.LENGTH_LONG).show();
            return;
        }

        OkHttpClient mOkHttpClient = new OkHttpClient();

        FormEncodingBuilder builder = new FormEncodingBuilder();
        if (null != map && !map.isEmpty())
            for (String key : map.keySet()) {
                builder.add(key, map.get(key) + "");
            }
        RequestBody requestBody= builder.build();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(requestBody)
                .build();
        try {
            mOkHttpClient.setConnectTimeout(5000, TimeUnit.MILLISECONDS);

            mOkHttpClient.newCall(request).enqueue(new Callback() {

                @Override
                public void onResponse(final Response response) throws IOException {

                    final String result = response.body().string();
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                                netRequestIterface.changeView(result, requestUrl);
                        }
                    });
                }

                @Override
                public void onFailure(Request request, IOException e) {

                    netRequestIterface.exception(e, requestUrl);

                }
            });
        } catch (Exception e) {

        }
    }
}
