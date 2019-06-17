package com.ssk.shardingjdbc.model;

import lombok.Data;

import java.util.Date;

/**
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-06-17-上午 10:32
 */
@Data
public class Orders {
    /**
     * 订单id
     */
    private String id;

    /**
     *  业务平台的订单id
     */
    private String parentOrdersUuid;
    /**
     * 业务平台的订单编号
     */
    private String parentOrdersId;
    /**
     * 订单来源
     */
    private String orderOrigin;
    /**
     * 订单类型
     */
    private String orderType;
    /**
     * 创建时间
     */
    private Date adddate;

}
