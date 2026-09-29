package com.hotelmanagement.controller;

import java.util.List;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.hotelmanagement.entity.Room;
import com.hotelmanagement.service.RoomService;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "http://localhost:5173")
public class RoomController {

    @Autowired
    private RoomService roomService;

    // Add Room
    @PostMapping
    public ResponseEntity<Room> addRoom(@Valid @RequestBody Room room) {
        return ResponseEntity.ok(roomService.addRoom(room));
    }

    // Get All Rooms
    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    // Get Room By ID
    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {

        Room room = roomService.getRoomById(id);

        if (room != null) {
            return ResponseEntity.ok(room);
        }

        return ResponseEntity.notFound().build();
    }

    // Update Room
    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(
            @PathVariable Long id,
            @RequestBody Room room) {

        Room updatedRoom = roomService.updateRoom(id, room);

        if (updatedRoom != null) {
            return ResponseEntity.ok(updatedRoom);
        }

        return ResponseEntity.notFound().build();
    }

    // Delete Room
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long id) {

        roomService.deleteRoom(id);

        return ResponseEntity.ok("Room deleted successfully");
    }
 // Check Room Availability
    @GetMapping("/{id}/availability")
    public ResponseEntity<Boolean> checkRoomAvailability(@PathVariable Long id) {

        boolean available = roomService.isRoomAvailable(id);

        return ResponseEntity.ok(available);
    }
   
}