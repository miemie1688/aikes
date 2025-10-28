package com.mindskip.xzs.repository;


import com.mindskip.xzs.viewmodel.student.user.UserProfile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserProfileMapper {

    // 1. 增 (Insert)
    int insert(UserProfile userProfile);

    // 2. 查 (Select) - 根据 ID
    UserProfile selectById(Integer id);

    // 2. 查 (Select) - 所有
    List<UserProfile> selectAll();

    // 3. 改 (Update)
    int update(UserProfile userProfile);

    // 4. 删 (Delete) - 根据 ID
    int deleteById(Integer id);

    // 4. 删 (Delete) - 根据 User ID (示例)
    int deleteByUserId(Integer userId);
}