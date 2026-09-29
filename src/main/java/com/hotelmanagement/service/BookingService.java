package com.hotelmanagement.service;

import java.time.LocalDate;

import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelmanagement.entity.Booking;
import com.hotelmanagement.entity.Room;
import com.hotelmanagement.repository.BookingRepository;
import com.hotelmanagement.repository.UserRepository;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomService roomService;
    
    @Autowired
    private UserRepository userRepository;


    // =====================================================
    // ADD BOOKING
    // =====================================================

    public Booking addBooking(Booking booking) {
    	
    	// Check User Exists
    	if (!userRepository.existsById(booking.getUserId())) {
    	    return null;
    	}

        // 1. Validate Status
        String status = booking.getStatus();

        if (status == null ||
                !(status.equals("PENDING") ||
                  status.equals("CONFIRMED") ||
                  status.equals("COMPLETED") ||
                  status.equals("CANCELLED"))) {
            return null;
        }

        // 2. Validate Dates
        LocalDate checkIn;
        LocalDate checkOut;

        try {
            checkIn = LocalDate.parse(booking.getCheckInDate());
            checkOut = LocalDate.parse(booking.getCheckOutDate());
        } catch (Exception e) {
            return null;
        }

        // Check-out must be after check-in
        if (!checkOut.isAfter(checkIn)) {
            return null;
        }

        // 3. Check Room
        Room room = roomService.getRoomById(booking.getRoomId());

        if (room == null) {
            return null;
        }

        // 4. Check Overlapping Booking
        boolean alreadyBooked =
                bookingRepository.existsOverlappingBooking(
                        booking.getRoomId(),
                        booking.getCheckInDate(),
                        booking.getCheckOutDate()
                );

        if (alreadyBooked) {
            return null;
        }

        // 5. Calculate Total Price
        long numberOfNights =
                ChronoUnit.DAYS.between(checkIn, checkOut);

        double roomPrice = room.getPrice();

        double totalPrice = roomPrice * numberOfNights;

        booking.setTotalPrice(totalPrice);

        // 6. Save Booking
        Booking savedBooking = bookingRepository.save(booking);

        System.out.println("=================================");
        System.out.println("BOOKING CREATED");
        System.out.println("ROOM ID: " + room.getId());
        System.out.println("ROOM NUMBER: " + room.getRoomNumber());
        System.out.println("ROOM PRICE: " + roomPrice);
        System.out.println("NUMBER OF NIGHTS: " + numberOfNights);
        System.out.println("TOTAL PRICE: " + totalPrice);
        System.out.println("=================================");

        return savedBooking;
    }


    // =====================================================
    // GET ALL BOOKINGS
    // =====================================================

    public List<Booking> getAllBookings() {

        return bookingRepository.findAll();
    }


    // =====================================================
    // GET BOOKINGS BY USER ID
    // =====================================================

    public List<Booking> getBookingsByUserId(
            Long userId) {

        return bookingRepository.findByUserId(
                userId
        );
    }


    // =====================================================
    // GET BOOKING BY ID
    // =====================================================

    public Booking getBookingById(
            Long id) {

        return bookingRepository
                .findById(id)
                .orElse(null);
    }


    // =====================================================
    // UPDATE BOOKING
    // =====================================================

    public Booking updateBooking(
            Long id,
            Booking booking) {

        Booking existingBooking =
                bookingRepository
                        .findById(id)
                        .orElse(null);


        if (existingBooking == null) {

            return null;
        }


        existingBooking.setUserId(
                booking.getUserId()
        );


        existingBooking.setRoomId(
                booking.getRoomId()
        );


        existingBooking.setCheckInDate(
                booking.getCheckInDate()
        );


        existingBooking.setCheckOutDate(
                booking.getCheckOutDate()
        );


        existingBooking.setGuests(
                booking.getGuests()
        );


        // -------------------------------------------------
        // Recalculate price from DATABASE
        // -------------------------------------------------

        try {

            LocalDate checkIn =
                    LocalDate.parse(
                            booking.getCheckInDate()
                    );

            LocalDate checkOut =
                    LocalDate.parse(
                            booking.getCheckOutDate()
                    );


            Room room =
                    roomService.getRoomById(
                            booking.getRoomId()
                    );


            if (room != null &&
                    checkOut.isAfter(checkIn)) {

                long nights =
                        ChronoUnit.DAYS.between(
                                checkIn,
                                checkOut
                        );


                double total =
                        room.getPrice() * nights;


                existingBooking.setTotalPrice(
                        total
                );
            }

        } catch (Exception e) {

            return null;
        }


        existingBooking.setStatus(
                booking.getStatus()
        );


        return bookingRepository.save(
                existingBooking
        );
    }
    
 // =====================================================
 // UPDATE PAYMENT STATUS
 // =====================================================

    public Booking updatePaymentStatus(Long id) {

        Booking booking =
                bookingRepository
                        .findById(id)
                        .orElse(null);

        if (booking == null) {
            return null;
        }

        // Update payment status
        booking.setPaymentStatus("Paid");

        // Confirm booking after payment
        booking.setStatus("CONFIRMED");

        // Make room unavailable
        Room room =
                roomService.getRoomById(
                        booking.getRoomId()
                );

        if (room != null) {

            room.setAvailable(false);

            roomService.updateRoom(
                    room.getId(),
                    room
            );
        }

        // Save booking
        return bookingRepository.save(booking);
    }

 // =====================================================
 // CANCEL BOOKING
 // =====================================================

 public Booking cancelBooking(Long id) {

     Booking booking =
             bookingRepository
                     .findById(id)
                     .orElse(null);

     if (booking == null) {
         return null;
     }

     // Change booking status
     booking.setStatus("CANCELLED");
     
     booking.setPaymentStatus("Cancelled");

     // Save cancelled booking
     Booking cancelledBooking =
             bookingRepository.save(booking);

     // Make room available again
     Room room =
             roomService.getRoomById(
                     booking.getRoomId()
             );

     if (room != null) {

         room.setAvailable(true);

         roomService.updateRoom(
                 room.getId(),
                 room
         );
     }

     return cancelledBooking;
 }

    // =====================================================
    // DELETE BOOKING
    // =====================================================

 public void deleteBooking(Long id) {

	    Booking booking =
	            bookingRepository
	                    .findById(id)
	                    .orElse(null);

	    if (booking != null) {

	        // Make room available again
	        Room room = roomService.getRoomById(booking.getRoomId());

	        if (room != null) {
	            room.setAvailable(true);
	            roomService.updateRoom(room.getId(), room);
	        }

	        // Delete booking
	        bookingRepository.deleteById(id);
	    }
	  }
    }
