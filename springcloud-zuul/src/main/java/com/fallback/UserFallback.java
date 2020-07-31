package com.fallback;

import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 当user微服务关闭之后会调用这个类
 */
@Component
public class UserFallback implements ZuulFallbackProvider {

    @Override
    public String getRoute() {
        return "user"; //指定哪一个微服务
    }

    @Override
    public ClientHttpResponse fallbackResponse() {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.BAD_GATEWAY;
            }
            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.BAD_GATEWAY.value();
            }
            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.BAD_GATEWAY.getReasonPhrase();
            }
            @Override
            public void close() {

            }
            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream("zuul.fallback().服务异常,请稍后重试.".getBytes());
            }
            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }
}