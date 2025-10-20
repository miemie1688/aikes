package com.mindskip.xzs.repository;

import com.mindskip.xzs.viewmodel.admin.exam.TExamCoverPath;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TExamCoverPathMapper {

    /**
     * 根据主键查询
     */
    TExamCoverPath selectByPrimaryKey(@Param("examId") Integer examId);

    /**
     * 插入记录
     */
    int insert(TExamCoverPath record);

    /**
     * 更新记录
     */
    int updateByPrimaryKey(TExamCoverPath record);

    /**
     * 删除记录
     */
    int deleteByPrimaryKey(@Param("examId") Integer examId);

    /*查询全部*/
    List<TExamCoverPath> getAllCoverPaths();


}
