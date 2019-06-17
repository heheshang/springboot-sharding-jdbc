package com.ssk.shardingjdbc.model;

import lombok.Data;

/**
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-06-17-上午 10:33
 */
@Data
public class OrdersDetail {
    /**
     * 订单明细id
     */
    private String id;

    /**
     * 订单id
     */
    private String ordersId;
    /**
     * 商品id
     */
    private String goodsId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品属性
     */
    private String goodsKindname;
}
