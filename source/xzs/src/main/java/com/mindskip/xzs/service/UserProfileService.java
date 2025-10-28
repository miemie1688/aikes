package com.example.demo.service;


import com.mindskip.xzs.viewmodel.student.user.UserProfile;

import java.util.List;

/**
 * 用户配置服务接口
 */
public interface UserProfileService {

    /**
     * 1. 增：添加新的用户配置
     * @param userProfile 待创建的用户配置
     * @return 包含生成 ID 的用户配置对象
     */
    UserProfile createProfile(UserProfile userProfile);

    /**
     * 2. 查：根据 ID 获取用户配置
     * @param id 用户配置 ID
     * @return 对应的用户配置，如果没有找到则返回 null
     */
    UserProfile getProfileById(Integer id);

    /**
     * 2. 查：获取所有用户配置
     * @return 所有用户配置列表
     */
    List<UserProfile> getAllProfiles();

    /**
     * 3. 改：更新用户配置
     * @param userProfile 包含更新信息和 ID 的对象
     * @return true 如果更新成功（至少一行受影响），否则 false
     */
    boolean updateProfile(UserProfile userProfile);

    /**
     * 4. 删：根据 ID 删除用户配置
     * @param id 用户配置 ID
     * @return true 如果删除成功，否则 false
     */
    boolean deleteProfileById(Integer id);
}