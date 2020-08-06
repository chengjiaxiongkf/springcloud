package com.user.service.impl;

import com.common.pojo.user.UserInfoPojo;
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
    public int insertUserInfo(UserInfoPojo userInfo) {
        return 0;
    }

    @Override
    public int updateUserInfo(UserInfoPojo userInfo) {
        return 0;
    }

    @Override
    public int deleteUserInfo(UserInfoPojo userInfo) {
        return 0;
    }

    @Override
    public UserInfoPojo getUserInfo(UserInfoPojo userInfo) {
        return userInfoMapper.getUserInfo(userInfo);
    }

    @Override
    public PageVO<UserInfoPojo> getUserInfoByPage(PageVO<UserInfoPojo> pageUserInfo) {
        return null;
    }
}
