package com.common.util;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author chengjiaxiong
 * @Date 2020/7/30 16:36
 */
public class JsonUtils {
    /**
     * 转换json
     * @param response
     * @param map
     */
    public static void sendJsonMessage(HttpServletResponse response, Object map) {
        PrintWriter printWriter = null;
        response.setContentType("application/json; charset=utf-8");
        try {
            printWriter = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(null==printWriter){
            return;
        }
        printWriter.write(JSONObject.toJSON(map).toString());
        printWriter.close();
        try {
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
