package com.mindskip.xzs.service;

import com.mindskip.xzs.viewmodel.Public.ReservationRequest;

import java.util.List;

public interface ReserveService {
    /**
     * 获取预约列表
     * @return 预约列表
     */
    List<ReservationRequest> getReserveList();
    /**
     * 删除预约
     * @param id 预约ID
     * @return 删除结果
     */
    Integer deleteReserve(Integer id);
     /**
     * 新增预约
     * @param reservationRequest 预约请求
     * @return 新增结果
     */
    Integer insertReserve(ReservationRequest reservationRequest);
    /**
     * 更新预约状态
     * @param reservationRequest 预约请求
     * @return 更新结果
     */
    Integer updateReserveStatus(ReservationRequest reservationRequest);
}
