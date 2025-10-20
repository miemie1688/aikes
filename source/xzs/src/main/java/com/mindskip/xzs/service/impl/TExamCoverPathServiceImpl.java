package com.mindskip.xzs.service.impl;

import com.mindskip.xzs.repository.TExamCoverPathMapper;
import com.mindskip.xzs.service.TExamCoverPathService;
import com.mindskip.xzs.viewmodel.admin.exam.TExamCoverPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * TExamCoverPathService 的实现类
 */
@Service
public class TExamCoverPathServiceImpl implements TExamCoverPathService {

    // 注入 Mapper 接口
    @Autowired
    private TExamCoverPathMapper examCoverPathMapper;

    /**
     * 保存试卷封面路径
     * 使用 @Transactional 确保操作的原子性
     * @param record 封面路径实体
     * @return true 成功, false 失败
     */
    @Override
    @Transactional
    public boolean saveExamCoverPath(TExamCoverPath record) {
        if (record == null || record.getExamId() == null || record.getCoverPath() == null) {
            // 简单校验
            return false;
        }
        // 调用 Mapper 的 insert 方法
        return examCoverPathMapper.insert(record) > 0;
    }

    /**
     * 根据试卷ID查询封面路径信息
     * @param examId 试卷ID
     * @return 封面路径实体，如果不存在则返回 null
     */
    @Override
    public TExamCoverPath getCoverPathById(Integer examId) {
        if (examId == null) {
            return null;
        }

        // 调用 Mapper 的 selectByPrimaryKey 方法
        TExamCoverPath examCoverPath = examCoverPathMapper.selectByPrimaryKey(examId);
System.out.println(examCoverPath.toString());
        return examCoverPath;
    }

    /**
     * 更新试卷封面路径
     * @param record 包含新的封面路径和 examId
     * @return true 成功, false 失败
     */
    @Override
    @Transactional
    public boolean updateCoverPath(TExamCoverPath record) {
        if (record == null || record.getExamId() == null || record.getCoverPath() == null) {
            return false;
        }
        // 调用 Mapper 的 updateByPrimaryKey 方法
        return examCoverPathMapper.updateByPrimaryKey(record) > 0;
    }

    /**
     * 根据试卷ID删除封面路径信息
     * @param examId 试卷ID
     * @return true 成功, false 失败
     */
    @Override
    @Transactional
    public boolean deleteCoverPath(Integer examId) {
        if (examId == null) {
            return false;
        }
        // 调用 Mapper 的 deleteByPrimaryKey 方法
        return examCoverPathMapper.deleteByPrimaryKey(examId) > 0;
    }
    @Override
    @Transactional
    public List<TExamCoverPath> getAllCoverPaths(){
        return examCoverPathMapper.getAllCoverPaths();
    }
}