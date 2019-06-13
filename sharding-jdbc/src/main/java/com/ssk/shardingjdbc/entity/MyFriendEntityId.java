package com.ssk.shardingjdbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-06-13-上午 10:41
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyFriendEntityId implements Serializable {

    @Column(name = "friend_id",nullable = false)
    private Long friendId;
    @Column(name = "user_id", nullable = false)
    private Long userId;
}
