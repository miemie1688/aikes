package com.mindskip.xzs.viewmodel.student.user;

import java.math.BigInteger;
import java.util.Map;

public class UserProfile {
    private Integer id;
    // 使用 Map<String, Object> 来映射 PostgreSQL 的 jsonb 类型
    private Map<String, Object> acmProblemsStatus;
    private String avatar;
    private String blog;
    private String mood;
    private Integer acceptedNumber;
    private Integer submissionNumber;
    private String github;
    private String school;
    private String major;
    private Integer userId;
    private BigInteger totalScore; // 对应 bigint
    private Map<String, Object> oiProblemsStatus;
    private String realName;
    private String language;

    // Getter 和 Setter 方法 (省略，但必须添加)
    // ...

    // 示例 Getter 和 Setter for ID

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public Integer getAcceptedNumber() {
        return acceptedNumber;
    }

    public void setAcceptedNumber(Integer acceptedNumber) {
        this.acceptedNumber = acceptedNumber;
    }

    public Integer getSubmissionNumber() {
        return submissionNumber;
    }

    public void setSubmissionNumber(Integer submissionNumber) {
        this.submissionNumber = submissionNumber;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigInteger getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(BigInteger totalScore) {
        this.totalScore = totalScore;
    }

    public Map<String, Object> getOiProblemsStatus() {
        return oiProblemsStatus;
    }

    public void setOiProblemsStatus(Map<String, Object> oiProblemsStatus) {
        this.oiProblemsStatus = oiProblemsStatus;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    // 示例 Getter 和 Setter for acmProblemsStatus
    public Map<String, Object> getAcmProblemsStatus() {
        return acmProblemsStatus;
    }

    public void setAcmProblemsStatus(Map<String, Object> acmProblemsStatus) {
        this.acmProblemsStatus = acmProblemsStatus;
    }

    // ... 其他字段的 Getter 和 Setter
}