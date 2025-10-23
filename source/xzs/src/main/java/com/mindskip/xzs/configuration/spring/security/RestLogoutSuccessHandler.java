package com.mindskip.xzs.configuration.spring.security;

import com.mindskip.xzs.base.SystemCode;
import com.mindskip.xzs.domain.User;
import com.mindskip.xzs.domain.UserEventLog;
import com.mindskip.xzs.event.UserEvent;
import com.mindskip.xzs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails; // 引入 UserDetails
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException; // 引入 IOException
import java.util.Date;


/**
 * @version 3.5.0
 * @description: 用户登出
 * Copyright (C), 2020-2025, 武汉思维跳跃科技有限公司
 * @date 2021/12/25 9:45
 */
@Component
public class RestLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    private final ApplicationEventPublisher eventPublisher;
    private final UserService userService;

    @Autowired
    public RestLogoutSuccessHandler(ApplicationEventPublisher eventPublisher, UserService userService) {
        this.eventPublisher = eventPublisher;
        this.userService = userService;
    }

    // 修复 onLogoutSuccess 方法签名，使其可以抛出 IOException
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        // 【核心修复】检查 authentication 是否为 null
        if (authentication != null) {

            // 获取 Principal。由于 Principal 可能是 null，这里也进行检查。
            Object principal = authentication.getPrincipal();

            // 确保 principal 是我们期望的 UserDetails 类型
            if (principal instanceof UserDetails) {

                UserDetails springUser = (UserDetails) principal;
                String username = springUser.getUsername();

                // 再次检查 username 是否有效
                if (username != null) {
                    // 获取用户信息，记录退出日志
                    User user = userService.getUserByUserName(username);

                    // 再次检查 user 是否为 null (以防数据库中用户已被删除)
                    if (user != null) {
                        UserEventLog userEventLog = new UserEventLog(user.getId(), user.getUserName(), user.getRealName(), new Date());
                        userEventLog.setContent(user.getUserName() + " 登出了爱克斯考试系统");
                        eventPublisher.publishEvent(new UserEvent(userEventLog));
                    }
                }
            }
        }

        // 无论用户是否认证，都返回退出成功的响应
        RestUtil.response(response, SystemCode.OK);
    }
}