package com.ssk.shardingjdbc.until;

import java.util.UUID;

/**
 * @author xiang.wei
 * @create 2018/2/1 16:44
 */
public class UUIDutil {

    /**
     * 生成id
     *
     * @return
     */
    public static String getUUID() {

        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }
}
