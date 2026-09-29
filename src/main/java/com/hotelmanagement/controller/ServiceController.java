package com.hotelmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hotelmanagement.entity.HotelService;
import com.hotelmanagement.service.HotelServiceService;

@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = "http://localhost:5173")
public class ServiceController {

    @Autowired
    private HotelServiceService hotelServiceService;

    // Add Service
    @PostMapping
    public ResponseEntity<HotelService> addService(
            @RequestBody HotelService service) {

        return ResponseEntity.ok(
                hotelServiceService.addService(service)
        );
    }

    // Get All Services
    @GetMapping
    public ResponseEntity<List<HotelService>> getAllServices() {

        return ResponseEntity.ok(
                hotelServiceService.getAllServices()
        );
    }

    // Get Service By ID
    @GetMapping("/{id}")
    public ResponseEntity<HotelService> getServiceById(
            @PathVariable Long id) {

        HotelService service =
                hotelServiceService.getServiceById(id);

        if (service != null) {
            return ResponseEntity.ok(service);
        }

        return ResponseEntity.notFound().build();
    }

    // Update Service
    @PutMapping("/{id}")
    public ResponseEntity<HotelService> updateService(
            @PathVariable Long id,
            @RequestBody HotelService service) {

        HotelService updatedService =
                hotelServiceService.updateService(id, service);

        if (updatedService != null) {
            return ResponseEntity.ok(updatedService);
        }

        return ResponseEntity.notFound().build();
    }

    // Delete Service
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteService(
            @PathVariable Long id) {

        hotelServiceService.deleteService(id);

        return ResponseEntity.ok(
                "Service deleted successfully"
        );
    }
}