package com.mindskip.xzs.viewmodel.admin.exam;

public class TExamCoverPath {

    /** 试卷ID，主键 */
    private Integer examId;

    /** 封面路径 */
    private String coverPath;

    // --- Getters and Setters ---

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    @Override
    public String toString() {
        return "TExamCoverPath{" +
                "examId=" + examId +
                ", coverPath='" + coverPath + '\'' +
                '}';
    }
}