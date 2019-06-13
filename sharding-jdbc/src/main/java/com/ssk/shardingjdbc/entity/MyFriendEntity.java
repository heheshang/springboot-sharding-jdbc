package com.ssk.shardingjdbc.entity;

import lombok.Data;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-06-13-上午 10:39
 */
@Entity
@Table(name = "MY_FRIEND")
@Data
public class MyFriendEntity implements Serializable {

    @EmbeddedId
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "userId",column = @Column(name = "user_id")),
            @AttributeOverride(name = "friendId",column = @Column(name = "friend_id"))
    })
    private MyFriendEntityId myFriendId;

    @Column(name = "remark", length = 16)
    private String remark;

    @Column(name = "add_date", nullable = false, columnDefinition = " datetime default now()")
    private LocalDateTime addDate;
}
