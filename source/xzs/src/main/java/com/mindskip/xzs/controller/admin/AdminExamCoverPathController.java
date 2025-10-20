package com.mindskip.xzs.controller.admin;

import com.mindskip.xzs.service.TExamCoverPathService;
import com.mindskip.xzs.viewmodel.admin.exam.TExamCoverPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员端试卷封面路径管理控制器
 */
@RestController
@RequestMapping(value = "api/admin/cover_path", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminExamCoverPathController {

    @Autowired
    private TExamCoverPathService examCoverPathService;

    // --- 1. 新增 (Create) ---
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> addCoverPath(@RequestBody TExamCoverPath record) {
        Map<String, Object> res = new HashMap<>();

        if (record == null || record.getExamId() == null || record.getCoverPath() == null) {
            res.put("code", 400);
            res.put("msg", "请求参数缺失 (examId 或 coverPath)");
            return ResponseEntity.badRequest().body(res);
        }

        String receivedPath = record.getCoverPath();
        String originalFilename = null;

        // 1. 【核心逻辑：还原原始文件名】
        // 示例输入: "static/img/img1.cfac12f7.png"
        // 目标输出: "img1.png"

        // 步骤 a: 提取文件名部分 (例如: img1.cfac12f7.png)
        int lastSlashIndex = receivedPath.lastIndexOf('/');
        String filenameWithHash = (lastSlashIndex != -1) ?
                receivedPath.substring(lastSlashIndex + 1) :
                receivedPath;

        // 步骤 b: 移除哈希值（移除倒数第二个点及其后的所有字符，直到最后一个点）
        int lastDot = filenameWithHash.lastIndexOf('.'); // .png 之前的点
        if (lastDot != -1) {
            int secondLastDot = filenameWithHash.lastIndexOf('.', lastDot - 1); // .hash 之前的点

            if (secondLastDot != -1) {
                // 成功提取：img1 (0到secondLastDot) + .png (lastDot到末尾)
                originalFilename = filenameWithHash.substring(0, secondLastDot) + filenameWithHash.substring(lastDot);
            }
        }

        // 如果提取失败，或者不符合预期的格式，则使用原始路径
        if (originalFilename == null) {
            // 如果无法解析，我们退回到使用原始路径或抛出警告
            // 为了满足 @/assets/imgN.png 的格式，如果解析失败，我们可以强制使用它
            // 但更安全的是保留原路径或返回错误
            originalFilename = filenameWithHash; // 保留文件名部分，但不进行格式转换
        }

        // 2. 【重构为目标存储格式】
        // 目标格式: "@/assets/img1.png"
        String finalCoverPath = "@" + "/assets/" + originalFilename;

        // 3. 【设置修改后的路径到记录中】
        record.setCoverPath(finalCoverPath);

        // 4. 【保存到数据库】
        boolean success = examCoverPathService.saveExamCoverPath(record);
        if (success) {
            res.put("code", 200);
            res.put("msg", "封面路径添加成功");
            return ResponseEntity.ok(res);
        } else {
            res.put("code", 400);
            res.put("msg", "封面路径添加失败，可能试卷ID已存在");
            return ResponseEntity.badRequest().body(res);
        }
    }

    // --- 2. 查询 (Retrieve) ---
    @GetMapping("selectById/{examId}")
    public ResponseEntity<Map<String, Object>> getCoverPath(@PathVariable Integer examId) {
        Map<String, Object> res = new HashMap<>();

        if (examId == null) {
            res.put("code", 400);
            res.put("msg", "试卷ID不能为空");
            return ResponseEntity.badRequest().body(res);
        }

        TExamCoverPath record = examCoverPathService.getCoverPathById(examId);
        if (record != null) {
            res.put("code", 200);
            res.put("msg", "查询成功");
            res.put("data", record);
            return ResponseEntity.ok(res);
        } else {
            res.put("code", 404);
            res.put("msg", "未找到对应试卷封面路径");
            return ResponseEntity.status(404).body(res);
        }
    }

    // --- 3. 更新 (Update) ---
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> updateCoverPath(@RequestBody TExamCoverPath record) {
        Map<String, Object> res = new HashMap<>();

        if (record == null || record.getExamId() == null || record.getCoverPath() == null) {
            res.put("code", 400);
            res.put("msg", "请求参数缺失 (examId 或 coverPath)");
            return ResponseEntity.badRequest().body(res);
        }

        boolean success = examCoverPathService.updateCoverPath(record);
        if (success) {
            res.put("code", 200);
            res.put("msg", "封面路径更新成功");
            return ResponseEntity.ok(res);
        } else {
            res.put("code", 400);
            res.put("msg", "封面路径更新失败，可能试卷ID不存在");
            return ResponseEntity.badRequest().body(res);
        }
    }

    // --- 4. 删除 (Delete) ---
    @DeleteMapping("/{examId}")
    public ResponseEntity<Map<String, Object>> deleteCoverPath(@PathVariable Integer examId) {
        Map<String, Object> res = new HashMap<>();

        if (examId == null) {
            res.put("code", 400);
            res.put("msg", "试卷ID不能为空");
            return ResponseEntity.badRequest().body(res);
        }

        boolean success = examCoverPathService.deleteCoverPath(examId);
        if (success) {
            res.put("code", 200);
            res.put("msg", "封面路径删除成功");
            return ResponseEntity.ok(res);
        } else {
            res.put("code", 400);
            res.put("msg", "删除失败，试卷ID不存在");
            return ResponseEntity.badRequest().body(res);
        }
    }

    // --- 5. 查询全部 ---
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllCoverPaths() {
        Map<String, Object> res = new HashMap<>();
        List<TExamCoverPath> coverPaths = examCoverPathService.getAllCoverPaths();

        res.put("code", 200);
        res.put("msg", "查询成功");
        res.put("data", coverPaths);

        return ResponseEntity.ok(res);
    }
}
