package com.zhubch.dbmusicer.Utils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.ResponseHandlerInterface;

/**
 * Created by zhubch on 1/16/16.
 */
public class HttpUtils {
    private static AsyncHttpClient client = new AsyncHttpClient();
    public static void get(String url,ResponseHandlerInterface responseHandlerInterface) {
        client.get(url, responseHandlerInterface);
    }

//    public static boolean isNetworkConnected(Context context) {
//        if (context != null) {
//            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
//                    .getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
//            if (mNetworkInfo != null) {
//                return mNetworkInfo.isAvailable();
//            }
//        }
//        return false;
//    }
}
