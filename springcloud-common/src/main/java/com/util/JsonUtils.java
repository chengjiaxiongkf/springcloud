package com.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JsonUtils {
    public static void getJsonMessages(HttpServletResponse response,Object obj) throws IOException{
        PrintWriter pw = response.getWriter();
        response.setContentType("text/json; charset=utf-8");
    }
}
