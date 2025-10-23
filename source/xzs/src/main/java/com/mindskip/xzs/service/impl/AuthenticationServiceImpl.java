package com.mindskip.xzs.service.impl;

import com.mindskip.xzs.configuration.property.SystemConfig;
import com.mindskip.xzs.domain.User;
import com.mindskip.xzs.service.AuthenticationService;
import com.mindskip.xzs.service.UserService;
import com.mindskip.xzs.utility.RsaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {

//set注入
// 声明字段，不再使用 final
private UserService userService;
    private SystemConfig systemConfig;

private DjangoPBKDF2Encoder djangoPBKDF2Encoder;
    // 移除原有的构造函数

    // Setter 注入方法
    @Autowired
    public void setDjangoPBKDF2Encoder(DjangoPBKDF2Encoder djangoPBKDF2Encoder) {
        this.djangoPBKDF2Encoder = djangoPBKDF2Encoder;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setSystemConfig(SystemConfig systemConfig) {
        this.systemConfig = systemConfig;
    }

    // Setter 注入方法（在解决循环依赖时特别有用）



    /**
     * @param username username
     * @param password password
     * @return boolean
     */
    @Override
    public boolean authUser(String username, String password) {
        User user = userService.getUserByUserName(username);
        return authUser(user, username, password);
    }


    @Override
    public boolean authUser(User user, String username, String password) {
        if (user == null) {
            return false;
        }
        String encodePwd = user.getPassword();
        if (null == encodePwd || encodePwd.length() == 0) {
            return false;
        }
        return checkPwd(password, encodePwd);
    }

    @Override
    public String pwdEncode(String password) {
        // 调用 DjangoPBKDF2Encoder 进行密码编码
        String encodePwd = djangoPBKDF2Encoder.encode(password);
        return encodePwd;
    }
    public boolean checkPwd(String rawPassword, String encodedPasswordFromDb) {
        // rawPassword: 用户输入的明文密码
        // encodedPasswordFromDb: 数据库中存储的 PBKDF2 哈希值
        return djangoPBKDF2Encoder.matches(rawPassword, encodedPasswordFromDb);
    }
    @Override
    public String pwdDecode(String encodePwd) {
        return RsaUtil.rsaDecode(systemConfig.getPwdKey().getPrivateKey(), encodePwd);
    }


}
