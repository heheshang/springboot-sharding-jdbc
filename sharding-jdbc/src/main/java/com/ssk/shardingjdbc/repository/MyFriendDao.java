package com.ssk.shardingjdbc.repository;

import com.ssk.shardingjdbc.entity.MyFriendEntity;
import com.ssk.shardingjdbc.entity.MyFriendEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-06-13-上午 10:43
 */
@Repository
public interface MyFriendDao extends JpaRepository<MyFriendEntity, MyFriendEntityId> {

}
