package com.hotelmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotelmanagement.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}