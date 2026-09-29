package com.hotelmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelmanagement.entity.HotelService;
import com.hotelmanagement.repository.HotelServiceRepository;

@Service
public class HotelServiceService {

    @Autowired
    private HotelServiceRepository hotelServiceRepository;

    // Add Service
    public HotelService addService(HotelService service) {
        return hotelServiceRepository.save(service);
    }

    // Get All Services
    public List<HotelService> getAllServices() {
        return hotelServiceRepository.findAll();
    }

    // Get Service By ID
    public HotelService getServiceById(Long id) {
        return hotelServiceRepository.findById(id).orElse(null);
    }

    // Update Service
    public HotelService updateService(Long id, HotelService service) {

        HotelService existingService =
                hotelServiceRepository.findById(id).orElse(null);

        if (existingService != null) {

            existingService.setServiceName(service.getServiceName());
            existingService.setPrice(service.getPrice());
            existingService.setDescription(service.getDescription());

            return hotelServiceRepository.save(existingService);
        }

        return null;
    }

    // Delete Service
    public void deleteService(Long id) {
        hotelServiceRepository.deleteById(id);
    }
}