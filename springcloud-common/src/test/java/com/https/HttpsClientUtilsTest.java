package com.https;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.util.HttpsClientUtils;
import org.junit.Test;
import java.io.FileOutputStream;
import java.io.IOException;

public class HttpsClientUtilsTest {
    /**
     * 请求https协议
     */
    @Test
    public void getHttps() throws IOException {
//        String uri = "https://www.baidu.com";
        String url = "https://location:8001";
        //跳转公会平台
//        url += "/szsb/?salesmanCode="+"FDSMI02467000216";//买保险入口
        url += "/api/szia/yb/accesstoken/get";
        JSONObject jo = new JSONObject();
        jo.put("clientId","123");
        jo.put("clinetSecret","123");
        byte[] bytes = HttpsClientUtils.doPost(url, JSONObject.toJSONString(jo));
        System.out.println(HttpsClientUtils.getMapByBytes(bytes)+"");
//        FileOutputStream fos = new FileOutputStream("D:/bing.txt");
//        fos.write(bytes);
//        fos.close();
//        System.out.println("done!");
//        byte[] bytes1 = "hello world".getBytes();        //Verify original content
//        System.out.println( new String(bytes1,"utf-8") );
    }
}
