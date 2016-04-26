package com.jalcoholapi.persistence.repository;

import com.jalcoholapi.persistence.model.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by weiliyang on 7/24/15.
 */
@Transactional
@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

}
