package com.mindskip.xzs.service.impl;


import com.mindskip.xzs.service.PasswordEncoderService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;

/**
 * 兼容 Django PBKDF2-SHA256 格式的密码编码器实现。
 * 实现了 PasswordEncoderService 接口。
 */
@Service
public class DjangoPBKDF2Encoder implements PasswordEncoderService {

    // --- 配置常量 (与 Django 默认设置保持一致) ---
    private static final String ALGORITHM_PREFIX = "pbkdf2_sha256";
    private static final int DEFAULT_ITERATIONS = 260000; // Django 4.x/5.x 默认值
    private static final int KEY_LENGTH_BYTES = 32;     // 256 bits
    private static final int SALT_LENGTH_BYTES = 12;    // Django 默认盐长度

    // 使用 SecureRandom 保证盐的生成安全
    private final SecureRandom secureRandom = new SecureRandom();

    /**
     * PBKDF2 哈希计算核心方法。
     */
    private byte[] pbkdf2(char[] password, byte[] salt, int iterations, int keyLength)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        // keyLength 是字节数，需要转换为位数 (keyLength * 8)
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength * 8);
        // Django 使用的是 PBKDF2WithHmacSHA256
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        return skf.generateSecret(spec).getEncoded();
    }

    // =========================================================================
    // 实现 PasswordEncoderService 接口方法
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public String encode(String rawPassword) {
        return encode(rawPassword, DEFAULT_ITERATIONS);
    }

    /**
     * 生成 Django 格式的 PBKDF2-SHA256 哈希字符串 (可指定迭代次数)。
     */
    public String encode(String rawPassword, int iterations) {
        System.out.println("--- 开始生成哈希密码 ---");
        try {
            // 1. 生成随机盐 (Salt)
            byte[] saltBytes = new byte[SALT_LENGTH_BYTES];
            secureRandom.nextBytes(saltBytes);

            // 将盐编码为 Base64 字符串，不使用填充
            // 注意：此处省略了原始代码中的兼容性注释，直接采用Base64无填充编码
            String salt = Base64.getEncoder().withoutPadding().encodeToString(saltBytes);

            // 2. 计算 PBKDF2 哈希
            byte[] hashBytes = pbkdf2(
                    rawPassword.toCharArray(),
                    salt.getBytes(StandardCharsets.UTF_8), // 核心函数要求字节数组
                    iterations,
                    KEY_LENGTH_BYTES
            );

            // 3. 将哈希结果编码为 Base64
            String encodedHash = Base64.getEncoder().encodeToString(hashBytes);

            // 4. 组合成 Django 格式的字符串
            String hashedPassword = String.format("%s$%d$%s$%s",
                    ALGORITHM_PREFIX,
                    iterations,
                    salt,
                    encodedHash
            );

            System.out.printf("生成结果: [Iterations: %d, Salt: %s, Hash: %s...]\n",
                    iterations, salt, encodedHash.substring(0, Math.min(30, encodedHash.length())));
            System.out.println("--- 哈希密码生成结束 ---");

            return hashedPassword;
        } catch (Exception e) {
            System.err.println("哈希密码生成失败: " + e.getClass().getName() + ": " + e.getMessage());
            // 生产环境中应抛出自定义异常，而不是返回 null
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean matches(String rawPassword, String hashedPassword) {
        System.out.println("\n--- 开始验证密码 ---");
        System.out.println("原始密码 (rawPassword): " + rawPassword);
        System.out.println("目标哈希 (hashedPassword): " + hashedPassword);

        String[] parts = parse(hashedPassword);
        if (parts == null) {
            return false;
        }

        try {
            int iterations = Integer.parseInt(parts[0]);
            String salt = parts[1];
            String encodedHash = parts[2];

            // 计算原始密码的哈希值
            byte[] hash = pbkdf2(
                    rawPassword.toCharArray(),
                    salt.getBytes(StandardCharsets.UTF_8),
                    iterations,
                    KEY_LENGTH_BYTES
            );

            // 将计算出的字节哈希值进行 Base64 编码
            String computedHash = Base64.getEncoder().encodeToString(hash);

            // 使用常量时间比较以防止时序攻击 (这里为了简洁，仍使用 equals，但生产环境应优化)
            boolean result = computedHash.equals(encodedHash);
            System.out.println("比较结果: " + (result ? "匹配" : "不匹配"));
            System.out.println("--- 密码验证结束 ---");
            return result;

        } catch (NumberFormatException e) {
            System.err.println("异常: 迭代次数解析错误: " + e.getMessage());
            return false;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.err.println("异常: PBKDF2 计算失败: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("捕获到其他异常: " + e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] parse(String hashedPassword) {
        System.out.println("\n--- 开始解析哈希密码 ---");
        if (hashedPassword == null || !hashedPassword.startsWith(ALGORITHM_PREFIX + "$")) {
            System.out.println("解析失败: 格式不正确或为空。");
            return null;
        }

        String[] parts = hashedPassword.split("\\$");
        if (parts.length != 4) {
            System.out.println("解析失败: 部分数量不正确，期望 4 部分，实际 " + parts.length + " 部分。");
            return null;
        }

        System.out.printf("解析结果: [算法: %s, 迭代次数: %s, 盐: %s]\n",
                parts[0], parts[1], parts[2]);
        System.out.println("--- 哈希密码解析结束 ---");

        // 返回 [迭代次数, 盐, 哈希值]
        return new String[]{parts[1], parts[2], parts[3]};
    }

    // =========================================================================
    // 单元测试/演示 (类似于原始的 main 方法)
    // =========================================================================


}