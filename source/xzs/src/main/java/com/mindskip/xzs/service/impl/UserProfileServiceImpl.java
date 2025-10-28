package com.mindskip.xzs.service.impl;


import com.mindskip.xzs.repository.UserProfileMapper;
import com.mindskip.xzs.viewmodel.student.user.UserProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户配置服务实现类
 */
@Service // 标记为 Spring Bean
public class UserProfileServiceImpl implements com.example.demo.service.UserProfileService {

    private final UserProfileMapper userProfileMapper;

    // 构造器注入
    public UserProfileServiceImpl(UserProfileMapper userProfileMapper) {
        this.userProfileMapper = userProfileMapper;
    }

    /**
     * 1. 增：添加新的用户配置
     */
    @Override
    @Transactional // 开启事务
    public UserProfile createProfile(UserProfile userProfile) {
        // userProfileMapper.insert() 在 XML 中配置了 useGeneratedKeys="true"
        // 所以执行后 userProfile 对象的 id 会被填充
        userProfileMapper.insert(userProfile);
        return userProfile;
    }

    /**
     * 2. 查：根据 ID 获取用户配置
     */
    @Override
    public UserProfile getProfileById(Integer id) {
        return userProfileMapper.selectById(id);
    }

    /**
     * 2. 查：获取所有用户配置
     */
    @Override
    public List<UserProfile> getAllProfiles() {
        return userProfileMapper.selectAll();
    }

    /**
     * 3. 改：更新用户配置
     */
    @Override
    @Transactional
    public boolean updateProfile(UserProfile userProfile) {
        // 实际应用中，这里应该先通过 ID 检查对象是否存在，再执行更新
        int rows = userProfileMapper.update(userProfile);
        return rows > 0;
    }

    /**
     * 4. 删：根据 ID 删除用户配置
     */
    @Override
    @Transactional
    public boolean deleteProfileById(Integer id) {
        int rows = userProfileMapper.deleteById(id);
        return rows > 0;
    }
}