package com.tyghpt;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 对接第三方同业工会平台的请求
 */
@RestController
public class TyghptHttpsRequestController {

    private final String url = "https://mi.szpisp.cn";

    /**
     * 返回map信息
     */
    @RequestMapping(value="/tyghpt/getInfoXml")
    public Map<String,Object> getMapInfo(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("a",request.getParameter("a"));
        resultMap.put("b",request.getParameter("b"));
        resultMap.put("c",request.getParameter("c"));
        return resultMap;
    }

    /**
     * 返回map信息
     */
    @RequestMapping(value="/tyghpt/getInfoJson",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String,Object> getMapInfoNew(HttpServletRequest request, HttpServletResponse response) throws  Exception {
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("a",request.getParameter("a"));
        resultMap.put("b",request.getParameter("b"));
        resultMap.put("c",request.getParameter("c"));
        return resultMap;
    }

    /**
     * 同业工会平台卖保险主页
     */
    @RequestMapping("/tyghpt/getIndex")
    public void getIndex(HttpServletRequest request, HttpServletResponse response) throws  Exception {
        String salesmanCode = request.getParameter("salesmanCode");
        String respUrl = url+"/szsb/?salesmanCode="+salesmanCode;
        response.sendRedirect(respUrl);//重定向
    }
}
