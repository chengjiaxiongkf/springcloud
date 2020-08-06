package com.user.service;

import com.common.pojo.user.UserInfoPojo;
import com.common.vo.PageVO;

/**
 * @Author chengjiaxiong
 * @Date 2020/7/28 14:22
 */
public interface UserInfoService {
    /**
     * 新增UserInfo
     * @param userInfo
     */
    int insertUserInfo(UserInfoPojo userInfo);

    /**
     * 修改UserInfo
     * @param userInfo
     */
    int updateUserInfo(UserInfoPojo userInfo);

    /**
     * 删除UserInfo
     * @param userInfo
     */
    int deleteUserInfo(UserInfoPojo userInfo);

    /**
     * 查询UserInfo
     * @param userInfo
     * @return
     */
    UserInfoPojo getUserInfo(UserInfoPojo userInfo);

    /**
     * 分页查询UserInfo
     * @param pageUserInfo
     * @return
     */
   PageVO<UserInfoPojo> getUserInfoByPage(PageVO<UserInfoPojo> pageUserInfo);
}
