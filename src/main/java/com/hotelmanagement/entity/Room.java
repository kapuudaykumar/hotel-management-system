package com.hotelmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String roomNumber;

    @NotBlank
    @Column(nullable = false)
    private String roomType;

    @Positive
    @Column(nullable = false)
    private double price;

    private String description;

    @Column(nullable = false)
    private boolean available;

    public Room() {

    }

    public Long getId() {

        return id;

    }

    public void setId(Long id) {

        this.id = id;

    }

    public String getRoomNumber() {

        return roomNumber;

    }

    public void setRoomNumber(String roomNumber) {

        this.roomNumber = roomNumber;

    }

    public String getRoomType() {

        return roomType;

    }

    public void setRoomType(String roomType) {

        this.roomType = roomType;

    }

    public double getPrice() {

        return price;

    }

    public void setPrice(double price) {

        this.price = price;

    }

    public String getDescription() {

        return description;

    }

    public void setDescription(String description) {

        this.description = description;

    }

    public boolean isAvailable() {

        return available;

    }

    public void setAvailable(boolean available) {

        this.available = available;

    }

}