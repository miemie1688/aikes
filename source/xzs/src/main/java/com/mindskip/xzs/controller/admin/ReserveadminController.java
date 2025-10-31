package com.mindskip.xzs.controller.admin;

import com.mindskip.xzs.base.RestResponse;
import com.mindskip.xzs.service.ReserveService;
import com.mindskip.xzs.service.impl.DjangoPBKDF2Encoder;
import com.mindskip.xzs.utility.MailUtil;
import com.mindskip.xzs.viewmodel.Public.ReservationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/admin/reserveadmin")
public class ReserveadminController {
    private final ReserveService reserveService;
    private final MailUtil mailUtil;
    private static final Logger log = (Logger) LoggerFactory.getLogger(ReserveadminController.class);
    public ReserveadminController(ReserveService reserveService, MailUtil mailUtil) {
        this.reserveService = reserveService;
        this.mailUtil = mailUtil;
    }
    @PostMapping("/submit")
    public RestResponse<Map<String, Object>> submitReservation(@RequestBody ReservationRequest reservationRequest) {
        // 1. 打印接收到的数据
        log.info("====== 收到新的预约请求 ======");
        log.info("预约 ID: {}", reservationRequest.getId());
        log.info("课程 ID: {}", reservationRequest.getCourseId());
        log.info("课程名称: {}", reservationRequest.getCourseName());
        log.info("姓名: {}", reservationRequest.getName());
        log.info("电话: {}", reservationRequest.getPhone());
        log.info("预约时间: {}", reservationRequest.getAppointment());
        log.info("============================");
        // 2. ***** 核心逻辑：将数据保存到数据库 *****
        Integer insertReserve = reserveService.insertReserve(reservationRequest);
        if (insertReserve == 0) {
            Map<String, Object> responseData = new HashMap<>();
            // 这里的 code=0 是为了兼容前端可能使用的特定结构
            responseData.put("code", 0);
            responseData.put("message", "预约提交失败");
            return RestResponse.ok(responseData);
        }
        //
        // ... 例如: reservationService.save(reservationRequest);
        // 3. 预约成功后，发送邮件通知管理员（可选）
        String subject = "【新预约】" + reservationRequest.getName() + " - " + reservationRequest.getCourseName();
        String content = String.format("收到新的预约：\n课程：《%s》\n姓名：%s\n电话：%s\n时间：%s",
                reservationRequest.getCourseName(),
                reservationRequest.getName(),
                reservationRequest.getPhone(),
                reservationRequest.getAppointment());

        // 假设通知邮件发送给管理员410052065@qq.com
        mailUtil.sendSimpleMail("410052065@qq.com", subject, content);
        log.info("管理员通知邮件发送成功");



        // 4. 返回成功响应（小程序需要 HTTP 200 状态码和 code=0 标记成功）
        //return RestResponse.ok(userVm);
        Map<String, Object> responseData = new HashMap<>();
        // 这里的 code=0 是为了兼容前端可能使用的特定结构
        responseData.put("code", 1);
        responseData.put("message", "预约提交成功");

        // 使用 RestResponse.ok(data) 返回包含 code 和 message 的 Map
        return RestResponse.ok(responseData);
    }
    @GetMapping("/list")
    public RestResponse<Map<String, Object>> getReserveList() {
        // 1. 返回预约列表
        log.info("====== 获取预约列表请求 ======");
        log.info("============================");
        List<ReservationRequest> reserveList = reserveService.getReserveList();

        Map<String, Object> responseData = new HashMap<>();
        // 这里的 code=0 是为了兼容前端可能使用的特定结构
        responseData.put("code", 1);
        responseData.put("message", "预约列表获取成功");
        responseData.put("data", reserveList);

        // 使用 RestResponse.ok(data) 返回包含 code 和 message 的 Map
        return RestResponse.ok(responseData);
    }

    /**
     * 删除预约
     * URL: /api/reserve/delete/{id}
     *
     * @param id 预约ID
     * @return 成功或失败的响应
     */
    @PostMapping("/delete/{id}")
    public RestResponse<Map<String, Object>> deleteReserve(@PathVariable Integer id) {
        // 1. 删除预约
        log.info("====== 删除预约请求 ======");
        log.info("预约 ID: " + id);
        log.info("============================");
        Integer deleteReserve = reserveService.deleteReserve(id);
        if (deleteReserve == 0) {
            Map<String, Object> responseData = new HashMap<>();
            // 这里的 code=0 是为了兼容前端可能使用的特定结构
            responseData.put("code", 0);
            responseData.put("message", "预约删除失败");
            return RestResponse.ok(responseData);
        }
        // 2. 返回成功响应（小程序需要 HTTP 200 状态码和 code=0 标记成功）
        Map<String, Object> responseData = new HashMap<>();
        // 这里的 code=0 是为了兼容前端可能使用的特定结构
        responseData.put("code", 1);

        responseData.put("message", "预约删除成功");
        return RestResponse.ok(responseData);
    }
    /**
     * 更新预约状态
     * URL: /api/reserve/updateStatus
     *
     * @param reservationRequest 预约请求
     * @return 成功或失败的响应
     */
    @PostMapping("/updateStatus")
    public RestResponse<Map<String, Object>> updateReserveStatus(@RequestBody ReservationRequest reservationRequest) {
        // 1. 更新预约状态
        Integer updateReserveStatus = reserveService.updateReserveStatus(reservationRequest);
        if (updateReserveStatus == 0) {
            Map<String, Object> responseData = new HashMap<>();
            // 这里的 code=0 是为了兼容前端可能使用的特定结构
            responseData.put("code", 0);
            responseData.put("message", "预约状态更新失败");
            return RestResponse.ok(responseData);
        }
        // 2. 返回成功响应（小程序需要 HTTP 200 状态码和 code=0 标记成功）
        Map<String, Object> responseData = new HashMap<>();
        // 这里的 code=0 是为了兼容前端可能使用的特定结构
        responseData.put("code", 1);
        responseData.put("message", "预约状态更新成功");
        return RestResponse.ok(responseData);
    }
}
