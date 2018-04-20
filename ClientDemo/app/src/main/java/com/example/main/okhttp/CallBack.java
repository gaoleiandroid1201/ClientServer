package com.example.main.okhttp;

import java.io.IOException;

/**
 *  项目中用到的回调接口
 */
public class CallBack {

    /**
     * 网络请求回调接口
     */
    public interface NetRequestIterface {
        void changeView(String result, String requestUrl);
        void exception(IOException e, String requestUrl);
    }

}
