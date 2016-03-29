package com.dingup.onlinetest.util;

import com.dingup.onlinetest.bean.HttpResContent;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by yanggavin on 16/3/19.
 */
public class HttpResUtil {
    /**
     * 全局可调节的是否支持JSONP开关
     */
    private static Boolean ENABLE_JSONP_REQUEST = false;
    /**
     * content内容类型
     */
    private static String CONTENT_TYPE_APPLICATION_JSON = "application/json";
    private static String CONTENT_TYPE_APPLICATION_JSONP = "application/x-json";

    public static <T> void writeResContent(HttpResContent<T> httpResContent,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        if (ENABLE_JSONP_REQUEST){
            writeResResult4Jsonp(httpResContent, request, response);
        } else {
            writeResResult(httpResContent, response);
        }
    }

    public static <T> void writeResResult(HttpResContent<T> httpResContent, HttpServletResponse response) throws Exception{
        try {
            if (response == null){
                throw new IllegalArgumentException("parameter error.");
            }
            response.setContentType(CONTENT_TYPE_APPLICATION_JSON);
            Gson gson = new Gson();
            String jsonRes = gson.toJson(httpResContent);
            PrintWriter pw = response.getWriter();
            pw.write(jsonRes);
            pw.close();
            pw.flush();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public static <T> void writeResResult4Jsonp(HttpResContent<T> httpResContent,
                                                HttpServletRequest request,
                                                HttpServletResponse response) throws Exception {
        try {
            if (request == null || response == null){
                throw new IllegalArgumentException("parameter error.");
            }
            String callback = request.getParameter("callback");
            response.setContentType(CONTENT_TYPE_APPLICATION_JSONP);
            Gson gson = new Gson();
            String jsonRes = gson.toJson(httpResContent);
            String retJsonpS = callback + "(" + jsonRes + ")";
            PrintWriter pw = response.getWriter();
            pw.write(retJsonpS);
            pw.close();
            pw.flush();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
