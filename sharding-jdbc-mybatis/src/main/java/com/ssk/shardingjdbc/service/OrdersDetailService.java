package com.ssk.shardingjdbc.service;

import com.ssk.shardingjdbc.model.OrdersDetail;

import java.util.List;

public interface OrdersDetailService {

    /**
     * @param ordersDetail
     * @return
     */
    boolean saveOrderDetail(OrdersDetail ordersDetail);

    /**
     * @param orderId
     * @return
     */
    List<OrdersDetail> getDetailByOrderId(String orderId);

    /**
     * @param name
     * @return
     */
    List<OrdersDetail> getDetailByName(String name);

}
