package com.example.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * Created by gaolei on 2017/5/5.
 */

public class Utils {
    private static ImageLoader mImageLoader;

    public static Utils utils;

    private Utils() {
    }

    public static Utils getInstance() {
        if (utils == null) {
            utils = new Utils();
            mImageLoader = ImageLoader.getInstance();
        }
        return utils;

    }
//    public Utils() {
//        mImageLoader = ImageLoader.getInstance();
//    }

    public void displayCircleImage(String url, ImageView view) {
        if (view == null) {
            return;
        }
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showImageOnLoading(R.drawable.default_photo).build();
        mImageLoader.displayImage(url, view, options);
    }

    public boolean isConnectingToInternet(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo[] info = manager.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++) {
                    System.out.println(info[i].getState());
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
        }
        return false;
    }
}
