package com.ssk.shardingjdbc.repository;

import com.ssk.shardingjdbc.entity.UserAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-06-13-上午 10:38
 */
@Repository
public interface UserAuthDao extends JpaRepository<UserAuthEntity,Integer> {

     List<UserAuthEntity> findByUserIdBetween(int start, int end);
}
