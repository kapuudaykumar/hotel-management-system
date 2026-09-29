package com.hotelmanagement.controller;

import java.util.List;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hotelmanagement.entity.Booking;
import com.hotelmanagement.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:5173")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Add Booking

    @PostMapping
    public ResponseEntity<?> addBooking(@Valid @RequestBody Booking booking){

        Booking savedBooking = bookingService.addBooking(booking);

        if (savedBooking == null) {
            return ResponseEntity.badRequest()
                    .body("Room is not available");
        }

        return ResponseEntity.ok(savedBooking);
    }

    // Get All Bookings
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getBookingsByUserId(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                bookingService.getBookingsByUserId(userId)
        );
    }

    // Get Booking By ID
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {

        Booking booking = bookingService.getBookingById(id);

        if (booking != null) {
            return ResponseEntity.ok(booking);
        }

        return ResponseEntity.notFound().build();
    }

    // Update Booking
    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(
            @PathVariable Long id,
            @RequestBody Booking booking) {

        Booking updatedBooking = bookingService.updateBooking(id, booking);

        if (updatedBooking != null) {
            return ResponseEntity.ok(updatedBooking);
        }

        return ResponseEntity.notFound().build();
    }
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Booking> cancelBooking(@PathVariable Long id) {

        Booking cancelledBooking = bookingService.cancelBooking(id);

        if (cancelledBooking != null) {
            return ResponseEntity.ok(cancelledBooking);
        }

        return ResponseEntity.notFound().build();
    }

    // Delete Booking
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id) {

        bookingService.deleteBooking(id);

        return ResponseEntity.ok("Booking deleted successfully");
    }
    
 // =====================================================
 // UPDATE PAYMENT STATUS
 // =====================================================

 @PutMapping("/{id}/payment")
 public ResponseEntity<?> updatePaymentStatus(
         @PathVariable Long id) {

     Booking booking =
             bookingService.updatePaymentStatus(id);

     if (booking == null) {

         return ResponseEntity
                 .notFound()
                 .build();
     }

     return ResponseEntity.ok(booking);
 }
}