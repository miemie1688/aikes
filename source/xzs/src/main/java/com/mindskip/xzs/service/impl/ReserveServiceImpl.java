package com.mindskip.xzs.service.impl;

import com.mindskip.xzs.repository.ReserveMapper;
import com.mindskip.xzs.service.ReserveService;
import com.mindskip.xzs.viewmodel.Public.ReservationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReserveServiceImpl implements ReserveService {
    @Autowired
    private ReserveMapper reserveMapper;

    @Override
    public List<ReservationRequest> getReserveList() {
        return reserveMapper.getReserveList();
    }

    @Override
    public Integer deleteReserve(Integer id) {
        return reserveMapper.deleteReserve(id);
    }
    @Override
    public Integer insertReserve(ReservationRequest reservationRequest) {
        return reserveMapper.insertReserve(reservationRequest);
    }
    @Override
    public Integer updateReserveStatus(ReservationRequest reservationRequest) {
        return reserveMapper.updateReserveStatus(reservationRequest);
    }
}
