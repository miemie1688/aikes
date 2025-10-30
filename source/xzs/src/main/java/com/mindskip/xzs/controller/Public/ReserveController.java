package com.mindskip.xzs.controller.Public;

import com.mindskip.xzs.base.RestResponse;
import com.mindskip.xzs.service.ReserveService;
import com.mindskip.xzs.utility.MailUtil;
import com.mindskip.xzs.viewmodel.admin.user.UserResponseVM;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mindskip.xzs.viewmodel.Public.ReservationRequest;

/**
 * 预约处理控制器 (ReserveController.java)
 * 假设您的后端项目运行在 localhost:8888 端口。
 */
@RestController
@RequestMapping("/api/reserve")
public class ReserveController {

    private final MailUtil mailUtil;
    private final ReserveService reserveService;

    // 构造函数注入 MailUtil
    public ReserveController(MailUtil mailUtil, ReserveService reserveService) {
        this.mailUtil = mailUtil;
        this.reserveService = reserveService;
    }

    /**
     * 处理小程序提交的预约数据
     * URL: /api/reserve/submit
     *
     * @param reservationRequest 包含 courseId, name, phone, appointment 等字段
     * @return 成功或失败的响应
     */
    @PostMapping("/submit")
    public RestResponse<Map<String, Object>> submitReservation(@RequestBody ReservationRequest reservationRequest) {
        // 1. 打印接收到的数据
        System.out.println("====== 收到新的预约请求 ======");
        System.out.println("预约 ID: " + reservationRequest.getId());
        System.out.println("课程 ID: " + reservationRequest.getCourseId());
        System.out.println("课程名称: " + reservationRequest.getCourseName());
        System.out.println("姓名: " + reservationRequest.getName());
        System.out.println("电话: " + reservationRequest.getPhone());
        System.out.println("预约时间: " + reservationRequest.getAppointment());
        System.out.println("============================");

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

        // 假设通知邮件发送给管理员
        mailUtil.sendSimpleMail("gl11223@qq.com", subject, content);
        System.out.println("管理员通知邮件发送成功");

        // 4. 返回成功响应（小程序需要 HTTP 200 状态码和 code=0 标记成功）
        //return RestResponse.ok(userVm);
        Map<String, Object> responseData = new HashMap<>();
        // 这里的 code=0 是为了兼容前端可能使用的特定结构
        responseData.put("code", 1);
        responseData.put("message", "预约提交成功");

        // 使用 RestResponse.ok(data) 返回包含 code 和 message 的 Map
        return RestResponse.ok(responseData);
    }

}
