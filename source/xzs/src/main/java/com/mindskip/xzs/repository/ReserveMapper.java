package com.mindskip.xzs.repository;

import com.mindskip.xzs.viewmodel.Public.ReservationRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReserveMapper {
    /**
     * 获取预约列表
     * @return 预约列表
     */
    List<ReservationRequest> getReserveList();

    Integer deleteReserve(Integer id);

    Integer insertReserve(ReservationRequest reservationRequest);
    // 更新预约状态
    Integer updateReserveStatus(ReservationRequest reservationRequest);
}
