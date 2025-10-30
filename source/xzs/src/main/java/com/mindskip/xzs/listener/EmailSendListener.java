package com.mindskip.xzs.listener;

import com.mindskip.xzs.domain.User;
import com.mindskip.xzs.event.OnRegistrationCompleteEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @version 3.5.0
 * @description:  The type Email send listener.
 * Copyright (C), 2020-2025, 武汉思维跳跃科技有限公司
 * @date 2021/12/25 9:45
 */
@Component
public class EmailSendListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    private static final Logger log = (Logger) LoggerFactory.getLogger(EmailSendListener.class);
    @Override
    @NonNull
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        log.info("User register Email sender :" + user.getUserName());
    }
}
