package com.filter;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author chengjiaxiong
 * @Date 2020/7/30 15:21
 */
@Component
public class PreZuulFilter extends ZuulFilter {

    private final Logger log = Logger.getLogger(PreZuulFilter.class);

    /**
     * 过滤器的类型。可选值有：
     * pre - 前置过滤
     * route - 路由后过滤
     * error - 异常过滤
     * post - 远程服务调用后过滤
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器执行顺序升序排序
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否启用此过滤器
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤内容
     * @return
     */
    @Override
    public Object run() {
        log.info("PreZuulFilter.run()start----------------------------------------");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        HttpServletResponse response = requestContext.getResponse();
        final List<String> needLoginUriList = new ArrayList<String>(){
            {
                add("ZL_USER");
            }
        };
        boolean needLogin = false;
        for (String needLoginUri : needLoginUriList) {
            if(request.getRequestURI().contains(needLoginUri)){
                needLogin = true;
                break;
            }
        }
        log.info("PreZuulFilter.run() needLogin:"+needLogin);
        if(needLogin){//需要登录
            String loginUser = request.getParameter("loginUser");
            String loginPassword = request.getParameter("loginPassword");
            //未登录成功
            if(!"admin".equals(loginUser) || !"123456".equals(loginPassword)){
                log.info("PreZuulFilter.run() user/password fail");
                requestContext.setSendZuulResponse(false);//终止请求
                PrintWriter printWriter = null;
                response.setContentType("application/json; charset=utf-8");
                try {
                    printWriter = response.getWriter();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Map map = new HashMap();
                map.put("resultCode","N");
                map.put("resultMsg","请输入正确的账号密码");
                if(null==printWriter){
                    return null;
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
        return null;
    }
}
