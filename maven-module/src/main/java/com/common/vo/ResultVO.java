package com.common.vo;

import lombok.Data;

import java.util.Map;

/**
 * @Author chengjiaxiong
 * @Date 2020/7/28 15:23
 */
@Data
public class ResultVO {
    //返回标识
    private String resultCode;
    //返回信息
    private String resultMsg;
    //返回数据
    private Object data;
}
