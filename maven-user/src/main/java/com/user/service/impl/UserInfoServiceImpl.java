package com.user.service.impl;

import com.common.pojo.user.UserInfo;
import com.common.vo.PageVO;
import com.user.mapper.UserInfoMapper;
import com.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @Author chengjiaxiong
 * @Date 2020/7/28 14:48
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public int insertUserInfo(UserInfo userInfo) {
        return 0;
    }

    @Override
    public int updateUserInfo(UserInfo userInfo) {
        return 0;
    }

    @Override
    public int deleteUserInfo(UserInfo userInfo) {
        return 0;
    }

    @Override
    public UserInfo getUserInfo(UserInfo userInfo) {
        return userInfoMapper.getUserInfo(userInfo);
    }

    @Override
    public PageVO<UserInfo> getUserInfoByPage(PageVO<UserInfo> pageUserInfo) {
        return null;
    }
}
