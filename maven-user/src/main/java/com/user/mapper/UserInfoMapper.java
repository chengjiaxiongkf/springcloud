package com.user.mapper;

import com.common.vo.PageVO;
import com.common.pojo.user.UserInfoPojo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author chengjiaxiong
 * @Date 2020/7/28 14:05
 */
@Mapper
@Repository
public interface UserInfoMapper {
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
     * 批量删除UserInfo
     * @param userInfo
     */
    int batchDeleteUserInfo(UserInfoPojo userInfo);

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
