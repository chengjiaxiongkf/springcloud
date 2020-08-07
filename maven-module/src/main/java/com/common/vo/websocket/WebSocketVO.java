package com.common.vo.websocket;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author chengjiaxiong
 * @Date 2020/8/6 19:23
 */
@Data
public class WebSocketVO implements Serializable {
    private String sessionId;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdData;
}
