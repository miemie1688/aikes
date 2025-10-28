package com.mindskip.xzs.controller.student;

import com.mindskip.xzs.service.UserProfileService;
import com.mindskip.xzs.viewmodel.student.user.UserProfile;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.HashMap; // 引入 HashMap 用于设置默认值

@RestController
@RequestMapping("/api/student/profiles")
public class UserProfileController {

    // 注入接口
    private final UserProfileService userProfileService;

    // 构造器注入
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping
    public UserProfile create(@RequestBody UserProfile profile) {
        return userProfileService.createProfile(profile);
    }

    @GetMapping("/{id}")
    public UserProfile get(@PathVariable Integer id) {
        return userProfileService.getProfileById(id);
    }

    // =======================================================
    // 新增接口：仅通过 userId 创建默认用户配置
    // =======================================================
    @PostMapping("/default-by-user/{userId}")
    public UserProfile createDefault(@PathVariable Integer userId) {
        // 1. 创建 UserProfile 对象
        UserProfile defaultProfile = new UserProfile();

        // 2. 设置提供的 userId
        defaultProfile.setUserId(userId);

        // 3. 设置默认值 (参考您的请求体)
        defaultProfile.setAcmProblemsStatus(new HashMap<>());           // {}
        defaultProfile.setAvatar("/public/avatar/default.png");         // /public/avatar/default.png
        defaultProfile.setBlog("");                                     // ""
        defaultProfile.setMood("");                                     // ""
        defaultProfile.setAcceptedNumber(0);                            // 0
        defaultProfile.setSubmissionNumber(0);                          // 0
        defaultProfile.setGithub("");                                   // ""
        defaultProfile.setSchool("");                                   // ""
        defaultProfile.setMajor("");                                    // ""
        defaultProfile.setTotalScore(BigInteger.ZERO);                                // 0
        defaultProfile.setOiProblemsStatus(new HashMap<>());            // {}
        defaultProfile.setRealName("");                                 // ""
        defaultProfile.setLanguage("");                                 // ""

        // 4. 调用 Service 层进行创建
        return userProfileService.createProfile(defaultProfile);
    }

    // ... 其他 CRUD 方法
}