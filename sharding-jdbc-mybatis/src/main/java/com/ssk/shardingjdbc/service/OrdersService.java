package com.ssk.shardingjdbc.service;

import com.ssk.shardingjdbc.model.Orders;

import java.util.List;

public interface OrdersService {

    /**
     * @param orders
     * @return
     */
    boolean saveOrders(Orders orders);

    /**
     * @param id
     * @return
     */
    Orders getOrderById(String id);

    /**
     * 分页查询数据
     * @param id
     * @param current
     * @param pageSize
     * @return
     */
    List<Orders> queryOrdersPage(String id,int current,int pageSize);

    /**
     * @param ids
     * @return
     */
    List<Orders> queryByIds(List<String> ids);

    /**
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<Orders> queryBetweenDate(String startTime,String endTime);
}
