package com.example.administrator.myapplication.api;

import com.fasterxml.jackson.annotation.JacksonAnnotationValue;

import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Administrator on 2016/12/10.
 */

public class Server {
    static OkHttpClient client;
    public final static String SERVER_ADDRESS="http://172.27.0.33:8080/membercenter/";
    static {
        CookieManager cookieManager=new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        client=new OkHttpClient.Builder().cookieJar(new JavaNetCookieJar(cookieManager)).build();

    }

    public  static  OkHttpClient getShareClient(){
        return  client;
    }

    public static Request.Builder requestBuildWithAPI(String api){
        return  new Request.Builder().url("Http://172.27.0.33:8080/membercenter/api/"+api);
    }
}
