package com.hotelmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelmanagement.entity.Room;
import com.hotelmanagement.repository.RoomRepository;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public Room addRoom(Room room) {
        return roomRepository.save(room);
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id).orElse(null);
    }

    public Room updateRoom(Long id, Room room) {

        Room existingRoom = roomRepository.findById(id).orElse(null);

        if (existingRoom != null) {

            existingRoom.setRoomNumber(room.getRoomNumber());
            existingRoom.setRoomType(room.getRoomType());
            existingRoom.setPrice(room.getPrice());
            existingRoom.setDescription(room.getDescription());
            existingRoom.setAvailable(room.isAvailable());

            return roomRepository.save(existingRoom);
        }

        return null;
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
    public boolean isRoomAvailable(Long roomId) {

        Room room = roomRepository.findById(roomId).orElse(null);

        if (room == null) {
            return false;
        }

        return room.isAvailable();
    }
}