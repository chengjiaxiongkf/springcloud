package com.common.pojo.user;

import lombok.Data;

/**
 * @Author chengjiaxiong
 * @Date 2020/7/28 14:05
 */
@Data
public class UserInfoPojo {
    private Integer id;         //主鍵ID
    private String userName;    //用戶姓名
    private String idNoType;    //证件类型
    private String idNo;        //证件号码
    private String sex;         //性别
    private String birthday;    //出生日期
    private String age;         //年龄
    private String isValid;     //有效标识
    private String createDate;  //创建日期
    private String createUser;  //创建人
    private String updateDate;  //修改日期
    private String updateUser;  //创建人
}
