package com.user.mapper;

import com.common.vo.PageVO;
import com.common.pojo.user.UserInfo;
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
     * 批量删除UserInfo
     * @param userInfo
     */
    int batchDeleteUserInfo(UserInfo userInfo);

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
