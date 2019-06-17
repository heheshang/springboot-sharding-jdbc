package com.ssk.shardingjdbc.mapper.sharding;

import com.ssk.shardingjdbc.model.OrdersDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrdersDetailMapper {

    /**
     * @param ordersDetail
     * @return
     */
    int insertDetail(OrdersDetail ordersDetail);

    /**
     * 根据订单ID查询订单明细
     *
     * @param orderId
     * @return
     */
    List<OrdersDetail> selectDetailByOrderId(String orderId);

    /**
     * @param name
     * @return
     */
    List<OrdersDetail> selectDetailByName(String name);

    /**
     * @param id
     * @return
     */
    List<OrdersDetail> selectDetailById(String id);
}
