package com.common.vo.websocket;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author chengjiaxiong
 * @Date 2020/8/7 12:27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerVO extends WebSocketVO {
    private String userId;
}
