package com.cafe.app.InnCafe.dao;

import com.cafe.app.InnCafe.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface IUserDao extends JpaRepository<User,Integer> {
    User findByEmailId(@Param("email") String email);
}
