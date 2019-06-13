package com.ssk.shardingjdbc.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;

/**
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-06-13-上午 10:32
 */
@Entity
@Table(name = "USER_AUTH", uniqueConstraints = {
        @UniqueConstraint(name = "user_auth_phone", columnNames = {"phone"}),
        @UniqueConstraint(name = "user_auth_email", columnNames = {"email"})
})
@Data
public class UserAuthEntity implements Serializable {

    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "phone", length = 16)
    private String phone;

    @Column(name = "email", length = 16)
    private String email;

    @Column(name = "local_password", length = 32,nullable = false)
    private String localPassword;
}
