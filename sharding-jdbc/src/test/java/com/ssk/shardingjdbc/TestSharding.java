package com.ssk.shardingjdbc;

import com.ssk.shardingjdbc.entity.MyFriendEntity;
import com.ssk.shardingjdbc.entity.MyFriendEntityId;
import com.ssk.shardingjdbc.entity.UserAuthEntity;
import com.ssk.shardingjdbc.repository.MyFriendDao;
import com.ssk.shardingjdbc.repository.UserAuthDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

/**
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-06-13-上午 11:04
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSharding {

    @Autowired
    MyFriendDao myFriendDao;

    @Autowired
    UserAuthDao userAuthDao;



    @Test
    public void testInsert() {

        MyFriendEntity friendEntity = new MyFriendEntity();
        friendEntity.setMyFriendId(new MyFriendEntityId(3L, 5L));
        friendEntity.setAddDate(LocalDateTime.now());
        myFriendDao.save(friendEntity);

        friendEntity.setMyFriendId(new MyFriendEntityId(5L, 5L));
        friendEntity.setAddDate(LocalDateTime.now());
        myFriendDao.save(friendEntity);

        friendEntity.setMyFriendId(new MyFriendEntityId(6L, 5L));
        friendEntity.setAddDate(LocalDateTime.now());
        myFriendDao.save(friendEntity);

        UserAuthEntity user = new UserAuthEntity();
        user.setUserId(2);
        user.setLocalPassword("123");
        userAuthDao.save(user);

    }


}
