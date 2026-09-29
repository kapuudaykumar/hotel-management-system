package com.hotelmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotelmanagement.entity.HotelService;

public interface HotelServiceRepository extends JpaRepository<HotelService, Long> {

}