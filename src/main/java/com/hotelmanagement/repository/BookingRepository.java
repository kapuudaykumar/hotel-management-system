package com.hotelmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hotelmanagement.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserId(Long userId);

    // Check whether the room is already booked
    // for the selected dates
    @Query("""
        SELECT COUNT(b) > 0
        FROM Booking b
        WHERE b.roomId = :roomId
        AND b.status <> 'CANCELLED'
        AND b.checkInDate < :checkOut
        AND b.checkOutDate > :checkIn
    """)
    boolean existsOverlappingBooking(
            @Param("roomId") Long roomId,
            @Param("checkIn") String checkIn,
            @Param("checkOut") String checkOut
    );

}