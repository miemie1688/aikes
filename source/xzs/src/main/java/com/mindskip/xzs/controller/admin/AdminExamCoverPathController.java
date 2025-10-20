package com.mindskip.xzs.controller.admin;

import com.mindskip.xzs.service.TExamCoverPathService;
import com.mindskip.xzs.viewmodel.admin.exam.TExamCoverPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员端试卷封面路径管理控制器
 * 路径前缀通常包含 /admin
 */
@RestController
@RequestMapping("/admin/cover_path")
public class AdminExamCoverPathController {

    @Autowired
    private TExamCoverPathService examCoverPathService;

    // --- 1. 新增 (Create) ---
    /**
     * POST /admin/cover_path
     * 创建一个新的试卷封面路径记录
     */
    @PostMapping
    public ResponseEntity<String> addCoverPath(@RequestBody TExamCoverPath record) {

        if (record == null || record.getExamId() == null || record.getCoverPath() == null) {
            return ResponseEntity.badRequest().body("请求参数缺失 (examId 或 coverPath)");
        }

        boolean success = examCoverPathService.saveExamCoverPath(record);

        if (success) {
            return ResponseEntity.ok("封面路径添加成功");
        } else {
            // 可能是 examId 已存在导致的插入失败
            return ResponseEntity.badRequest().body("封面路径添加失败，可能试卷ID已存在");
        }
    }

    // --- 2. 查询 (Retrieve) ---
    /**
     * GET /admin/cover_path/{examId}
     * 根据试卷ID查询封面路径详情
     */
    @GetMapping("selectById/{examId}")
    public ResponseEntity<TExamCoverPath> getCoverPath(@PathVariable Integer examId) {

        if (examId == null) {
            return ResponseEntity.badRequest().build();
        }

        TExamCoverPath record = examCoverPathService.getCoverPathById(examId);

        if (record != null) {

            return ResponseEntity.ok(record);
        } else {

            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    // --- 3. 更新 (Update) ---
    /**
     * PUT /admin/cover_path
     * 更新试卷ID对应的封面路径
     */
    @PutMapping
    public ResponseEntity<String> updateCoverPath(@RequestBody TExamCoverPath record) {
        if (record == null || record.getExamId() == null || record.getCoverPath() == null) {
            return ResponseEntity.badRequest().body("请求参数缺失 (examId 或 coverPath)");
        }

        boolean success = examCoverPathService.updateCoverPath(record);

        if (success) {
            return ResponseEntity.ok("封面路径更新成功");
        } else {
            return ResponseEntity.badRequest().body("封面路径更新失败，可能试卷ID不存在");
        }
    }

    // --- 4. 删除 (Delete) ---
    /**
     * DELETE /admin/cover_path/{examId}
     * 根据试卷ID删除封面路径记录
     */
    @DeleteMapping("/{examId}")
    public ResponseEntity<String> deleteCoverPath(@PathVariable Integer examId) {
        if (examId == null) {
            return ResponseEntity.badRequest().build();
        }

        boolean success = examCoverPathService.deleteCoverPath(examId);

        if (success) {
            return ResponseEntity.ok("封面路径删除成功");
        } else {
            return ResponseEntity.badRequest().body("删除失败，试卷ID不存在"); // 400 Bad Request
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<TExamCoverPath>> getAllCoverPaths(){
        List<TExamCoverPath> coverPaths = examCoverPathService.getAllCoverPaths();
        return ResponseEntity.ok(coverPaths);
    }
}