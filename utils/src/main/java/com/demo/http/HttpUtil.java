package com.demo.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpUtil {

    public static String get(String url) {
        String res = null;
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(new HttpGet(url));) {
            //解析响应
            if (response.getStatusLine().getStatusCode() == 200) {
                res = EntityUtils.toString(response.getEntity(), "utf8");
            }
        } catch (Exception e) {
            res = e.getMessage();
        }
        return res;
    }

    public static String post(String url, String params) {
        String res;

        // 创建httpPost
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setEntity(new StringEntity(params, "UTF-8"));

        try (CloseableHttpClient httpclient = HttpClients.createDefault();
             CloseableHttpResponse response = httpclient.execute(httpPost);) {
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                res = EntityUtils.toString(responseEntity, "UTF-8");
            } else {
                res = "请求远程服务异常，请联系管理员！";
            }
        } catch (Exception e) {
            res = e.getMessage();
        }
        return res;
    }

    public static void main(String[] args) {
        double b = 40.845855;
        for (int i = 0; i < 20; i++) {
            HttpUtil.post("http://127.0.0.1/driver/report", "{\"driverId\":\"761472032\", \"longitude\": 111.691053,\"latitude\": " + b + ",\"lastTime\": 123456}");
            b = b - i * 0.0001;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
