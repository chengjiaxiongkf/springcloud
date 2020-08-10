package com.filter;

import com.common.contants.CommonContants;
import com.common.contants.login.LoginContants;
import com.common.util.CookieUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.redis.contants.RedisContants;
import com.redis.util.RedisUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author chengjiaxiong
 * @Date 2020/7/30 15:21
 */
@Component
public class PreZuulFilter extends ZuulFilter {

    private final Logger log = Logger.getLogger(PreZuulFilter.class);

    @Autowired
    private RedisUtils redisUtils;

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
        if(!needLogin){//不需要认证登录
            return null;
        }
        String cookie = CookieUtils.getCookieValue(request, LoginContants.COOKIE_SSOCOOKIE);
        if(!StringUtils.isEmpty(cookie) && checkToken(cookie)){//校验登陆token是否存在
//            requestContext.setSendZuulResponse(false);//终止请求
            //更新登录失效
            return null;
        }
        //跳转到登陆页面
        try {
            response.sendRedirect(CommonContants.LOCATION_HTTP+request.getRemoteHost()+CommonContants.LOCATION_LOGIN_URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * redis缓存认证
     * @param key
     * @return
     */
    private boolean checkToken(String key){
        if(null!=redisUtils.get(RedisContants.REDIS_LOGIN_TOKEN+key)){
            //更新token登录失效
            redisUtils.expire(RedisContants.REDIS_LOGIN_TOKEN+key,LoginContants.LOGIN_VALID_TIME);
            return true;
        }
        return false;
    }
}
