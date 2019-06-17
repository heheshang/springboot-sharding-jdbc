package com.ssk.shardingjdbc.mapper.sharding;

import com.ssk.shardingjdbc.model.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrdersMapper {

    /**
     * @param orders
     * @return
     */
    int insertOrders(Orders orders);

    /**
     * @param id
     * @return
     */
    Orders selectById(String id);

    /**
     * 分页查询
     *
     * @param id
     * @param current
     * @param pageSize
     * @return
     */
    List<Orders> queryOrdersPage(
            @Param("id") String id,
            @Param("current") int current,
            @Param("pageSize") int pageSize);

    /**
     * 根据id批量查询
     * @param ids
     * @return
     */
    List<Orders> queryInById(@Param("ids") List<String> ids);

    /**
     * @param startTime
     * @param endTime
     * @return
     */
    List<Orders> queryBetweenDate(@Param("startTime") String startTime,
                                  @Param("endTime") String endTime);
}
