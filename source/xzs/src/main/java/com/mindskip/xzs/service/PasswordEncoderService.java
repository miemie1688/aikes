package com.mindskip.xzs.service;

/**
 * 密码编码和验证的服务接口。
 * 对应于系统中处理用户密码存储和认证的逻辑。
 */
public interface PasswordEncoderService {

    /**
     * 生成 Django 格式的 PBKDF2-SHA256 哈希字符串。
     * 格式: pbkdf2_sha256$iterations$salt$hash
     *
     * @param rawPassword 原始密码
     * @return Django 格式的哈希密码字符串
     */
    String encode(String rawPassword);

    /**
     * 验证原始密码是否与存储的哈希密码匹配。
     *
     * @param rawPassword 原始密码
     * @param hashedPassword 存储的哈希密码 (pbkdf2_sha256$iterations$salt$hash)
     * @return 密码是否匹配
     */
    boolean matches(String rawPassword, String hashedPassword);

    /**
     * 解析 Django 格式的哈希密码字符串。
     * @param hashedPassword 存储的哈希密码 (pbkdf2_sha256$iterations$salt$hash)
     * @return 包含 [迭代次数, 盐, 哈希值] 的字符串数组，如果解析失败则返回 null。
     */
    String[] parse(String hashedPassword);
}