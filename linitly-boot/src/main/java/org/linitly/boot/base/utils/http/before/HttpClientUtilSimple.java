package org.linitly.boot.base.utils.http.before;//package com.zhencang.pipe.gallery.utils.http;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import com.alibaba.fastjson.JSON;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.http.HttpEntity;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.methods.HttpRequestBase;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.message.BasicHeader;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.util.EntityUtils;
//
//@Slf4j
//public class HttpClientUtil {
//
//    /**
//     * setConnectTimeout：设置连接超时时间，单位毫秒。
//     * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
//     * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
//     * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
//     */
//    private static final int SOCKET_TIMEOUT = 6000;
//    private static final int CONNECT_TIMEOUT = 6000;
//    private static final int CONNECT_REQUEST_TIMEOUT = 6000;
//    private static final String ENCODING = "UTF-8";
//
//    private static RequestConfig requestConfig = RequestConfig.custom()
//            .setSocketTimeout(SOCKET_TIMEOUT)
//            .setConnectTimeout(CONNECT_TIMEOUT)
//            .setConnectionRequestTimeout(CONNECT_REQUEST_TIMEOUT)
//            .build();
//
//    /**
//     * 发送 post请求
//     *
//     * @param httpUrl 地址
//     */
//    public static String sendHttpPost(String httpUrl) {
//        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
//        return sendHttpPost(httpPost);
//    }
//
//    /**
//     * 发送 post请求
//     *
//     * @param httpUrl 地址
//     * @param params  参数(格式:key1=value1&key2=value2)
//     */
//    public static String sendHttpPost(String httpUrl, String params) {
//        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
//        try {
//            // 设置参数
//            StringEntity stringEntity = new StringEntity(params, ENCODING);
//            stringEntity.setContentType("application/x-www-form-urlencoded");
//            httpPost.setEntity(stringEntity);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return sendHttpPost(httpPost);
//    }
//
//    /**
//     * 发送 post请求
//     *
//     * @param httpUrl 地址
//     * @param maps    参数
//     */
//    public static String sendHttpPost(String httpUrl, Map<String, String> headers, Map<String, String> maps) {
//        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
//        // 创建参数队列
//        List<NameValuePair> nameValuePairs = new ArrayList<>();
//        if (null != maps) {
//            for (String key : maps.keySet()) {
//                nameValuePairs.add(new BasicNameValuePair(key, maps.get(key)));
//            }
//        }
//        if (null != headers) {
//            for (String key : headers.keySet()) {
//                httpPost.setHeader(key, headers.get(key));
//            }
//        }
//        try {
//            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, ENCODING));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            log.error("不支持转换为UTF-8格式");
//        }
//        return sendHttpPost(httpPost);
//    }
//
//    public String sendHttpPost(String httpUrl, Map<String, String> maps) {
//        return sendHttpPost(httpUrl, null, maps);
//    }
//
//
//    /**
//     * 发送Post请求
//     *
//     * @param httpPost
//     * @return
//     */
//    private static String sendHttpPost(HttpPost httpPost) {
//        return sendHttpRequest(httpPost);
//    }
//
//    /**
//     * 发送 get请求
//     *
//     * @param httpUrl
//     */
//    public static String sendHttpGet(String httpUrl) {
//        HttpGet httpGet = new HttpGet(httpUrl);// 创建get请求
//        return sendHttpGet(httpGet, null);
//    }
//
//    /**
//     * 发送Get请求
//     *
//     * @param httpGet
//     * @return
//     */
//    public static String sendHttpGet(HttpGet httpGet, Map<String, String> headers) {
//        if (null != headers) {
//            for (String key : headers.keySet()) {
//                httpGet.setHeader(key, headers.get(key));
//            }
//        }
//        return sendHttpRequest(httpGet);
//    }
//
//    /**
//     * post请求json
//     *
//     * @param httpUrl
//     * @param json
//     * @return
//     */
//    public static String sendHttpPostJson(String httpUrl, String json) {
//        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
//        StringEntity reqEntity = new StringEntity(json, ENCODING);
//        reqEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
//        httpPost.setEntity(reqEntity);
//        return sendHttpPost(httpPost);
//    }
//
//    /**
//     * post请求对象
//     *
//     * @param httpUrl
//     * @param object
//     * @return
//     */
//    public static String sendHttpPostJson(String httpUrl, Object object) {
//        String json = JSON.toJSONString(object);
//        return sendHttpPostJson(httpUrl, json);
//    }
//
//    private static String sendHttpRequest(HttpRequestBase requestBase) {
//        CloseableHttpClient httpClient = null;
//        CloseableHttpResponse response = null;
//        HttpEntity entity = null;
//        String responseContent = null;
//        try {
//            // 创建默认的httpClient实例.
//            httpClient = HttpClients.createDefault();
//            requestBase.setConfig(requestConfig);
//            // 执行请求
//            response = httpClient.execute(requestBase);
//            entity = response.getEntity();
//            responseContent = EntityUtils.toString(entity, ENCODING);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            release(httpClient, response);
//        }
//        return responseContent;
//    }
//
//    private static void release(CloseableHttpClient httpClient, CloseableHttpResponse response) {
//        try {
//            // 关闭连接,释放资源
//            if (response != null) {
//                response.close();
//            }
//            if (httpClient != null) {
//                httpClient.close();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            log.error("CloseableHttpClient、CloseableHttpResponse资源释放失败");
//        }
//    }
//}
