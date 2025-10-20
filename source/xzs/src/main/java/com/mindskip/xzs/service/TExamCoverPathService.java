package com.mindskip.xzs.service;

import com.mindskip.xzs.viewmodel.admin.exam.TExamCoverPath;

import java.util.List;

/**
 * t_exam_cover_path 表的业务逻辑接口
 */
public interface TExamCoverPathService {

    /**
     * 保存试卷封面路径
     * @param record 封面路径实体
     * @return true 成功, false 失败
     */
    boolean saveExamCoverPath(TExamCoverPath record);

    /**
     * 根据试卷ID查询封面路径信息
     * @param examId 试卷ID
     * @return 封面路径实体，如果不存在则返回 null
     */
    TExamCoverPath getCoverPathById(Integer examId);

    /**
     * 更新试卷封面路径
     * @param record 包含新的封面路径和 examId
     * @return true 成功, false 失败
     */
    boolean updateCoverPath(TExamCoverPath record);

    /**
     * 根据试卷ID删除封面路径信息
     * @param examId 试卷ID
     * @return true 成功, false 失败
     */
    boolean deleteCoverPath(Integer examId);

    /**
     * 查询所有试卷封面路径信息
     * @return 所有封面路径实体列表
     */
    List<TExamCoverPath> getAllCoverPaths();
}