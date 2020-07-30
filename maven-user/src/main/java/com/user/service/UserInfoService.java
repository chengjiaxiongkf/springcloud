package com.user.service;

import com.common.pojo.user.UserInfo;
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
    int insertUserInfo(UserInfo userInfo);

    /**
     * 修改UserInfo
     * @param userInfo
     */
    int updateUserInfo(UserInfo userInfo);

    /**
     * 删除UserInfo
     * @param userInfo
     */
    int deleteUserInfo(UserInfo userInfo);

    /**
     * 查询UserInfo
     * @param userInfo
     * @return
     */
    UserInfo getUserInfo(UserInfo userInfo);

    /**
     * 分页查询UserInfo
     * @param pageUserInfo
     * @return
     */
   PageVO<UserInfo> getUserInfoByPage(PageVO<UserInfo> pageUserInfo);
}
