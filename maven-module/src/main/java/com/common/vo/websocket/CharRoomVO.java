package com.common.vo.websocket;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author chengjiaxiong
 * @Date 2020/8/8 9:53
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CharRoomVO extends WebSocketVO {
    private String userId;
    private String userName;
}
