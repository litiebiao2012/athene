package com.athene.api.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by fe on 16/9/27.
 */
public class HttpUrlConnection {

    public static final Logger logger = LoggerFactory.getLogger(HttpUrlConnection.class);

    public static final String DEFAULT_ENCODING = "UTF-8";

    public static void post(String url,String jsonData) {
        try {
            logger.info("request url : {} , data : {}",url,jsonData);
            URL urls = new URL(url);
            HttpURLConnection httpConn = (HttpURLConnection) urls.openConnection();


            httpConn.setDoOutput(true);// 使用 URL 连接进行输出
            httpConn.setDoInput(true);// 使用 URL 连接进行输入
            httpConn.setUseCaches(false);// 忽略缓存
            httpConn.setRequestMethod("POST");// 设置URL请求方法
            String requestString = "客服端要以以流方式发送到服务端的数据...";


            byte[] requestStringBytes = requestString.getBytes("UTF-8");
            httpConn.setRequestProperty("Content-length", "" + requestStringBytes.length);
            httpConn.setRequestProperty("Content-Type", "application/json");
            httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            httpConn.setRequestProperty("Charset", "UTF-8");


            OutputStream outputStream = httpConn.getOutputStream();
            outputStream.write(requestStringBytes);
            outputStream.close();
            int responseCode = httpConn.getResponseCode();

            logger.info("response code : {}",responseCode);
            if (HttpURLConnection.HTTP_OK == responseCode) {// 连接成功
                StringBuffer sb = new StringBuffer();
                String readLine;
                BufferedReader responseReader;
                responseReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), DEFAULT_ENCODING));
                while ((readLine = responseReader.readLine()) != null) {
                    sb.append(readLine).append("\n");
                }
                responseReader.close();
                logger.info("response data : {}", sb.toString());
            }
        } catch (Exception e) {
            logger.error("post server error ! e : {}",e);
        }
    }
}
