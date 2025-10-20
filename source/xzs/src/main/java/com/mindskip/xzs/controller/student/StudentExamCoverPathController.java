package com.mindskip.xzs.controller.student;

import com.mindskip.xzs.service.TExamCoverPathService;
import com.mindskip.xzs.viewmodel.admin.exam.TExamCoverPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 学生端试卷封面路径查询控制器
 * 路径前缀通常包含 /student 或 /public
 */
@RestController
@RequestMapping("/student/cover_path")
public class StudentExamCoverPathController {

    @Autowired
    private TExamCoverPathService examCoverPathService;

    // --- 1. 查询 (Retrieve) ---
    /**
     * GET /student/cover_path/{examId}
     * 根据试卷ID查询封面路径信息
     * 这是学生唯一允许的操作
     */
    @GetMapping("select/{examId}")
    public ResponseEntity<TExamCoverPath> getCoverPathForStudent(@PathVariable Integer examId) {
        if (examId == null) {
            return ResponseEntity.badRequest().build();
        }

        // 核心逻辑：调用 Service 层方法进行查询
        TExamCoverPath record = examCoverPathService.getCoverPathById(examId);

        if (record != null) {
            // 返回信息给学生
            return ResponseEntity.ok(record);
        } else {
            // 试卷不存在，返回 404
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<TExamCoverPath>> getAllCoverPaths(){
        List<TExamCoverPath> coverPaths = examCoverPathService.getAllCoverPaths();
        return ResponseEntity.ok(coverPaths);
    }
}