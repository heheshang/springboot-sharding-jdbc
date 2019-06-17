package com.ssk.shardingjdbc.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.ssk.shardingjdbc.model.OrdersDetail;
import com.ssk.shardingjdbc.mapper.sharding.OrdersDetailMapper;

@Slf4j
@Service
public class OrdersDetailServiceImpl implements OrdersDetailService{

    @Autowired
    private OrdersDetailMapper ordersDetailMapper;

    @Override
    public boolean saveOrderDetail(OrdersDetail ordersDetail) {
        if (ordersDetail == null) {
            return false;
        }
        int result = ordersDetailMapper.insertDetail(ordersDetail);
        return result == 1 ? true : false;
    }

    @Override
    public List<OrdersDetail> getDetailByOrderId(String orderId) {
        if (StringUtils.isEmpty(orderId)) {
            return null;
        }
        return ordersDetailMapper.selectDetailByOrderId(orderId);
    }

    @Override
    public List<OrdersDetail> getDetailByName(String name) {
        if (StringUtils.isNotBlank(name)) {
            return null;
        }
        return ordersDetailMapper.selectDetailByName(name);
    }
}
