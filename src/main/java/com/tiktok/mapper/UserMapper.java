package com.tiktok.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tiktok.bean.UserData;
import com.tiktok.bean.UserFile;
import com.tiktok.bean.UserFollow;
import com.tiktok.bean.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserInfo> {

    /**
     * 以用户名去查找获取UserInfo数据
     * @param username
     * @return
     */
    UserInfo selectByUsername(String username);

    /**
     * 以id去查找获取UserInfo数据
     * @param userid
     * @return
     */
    UserInfo selectByIdFromUserInfo(Integer userid);

    /**
     * 以id去查找获取UserFile数据
     * @param userid
     * @return
     */
    UserFile selectByIdFromUserFile(Integer userid);

    /**
     * 以id去查找获取UserFollow数据
     * @param userid
     * @return
     */
    UserFollow selectByIdFromUserFollow(Integer userid);

    /**
     * 以id去查找获取Userdata数据
     * @param userid
     * @return
     */
    UserData selectByIdFromUserData(Integer userid);
}
