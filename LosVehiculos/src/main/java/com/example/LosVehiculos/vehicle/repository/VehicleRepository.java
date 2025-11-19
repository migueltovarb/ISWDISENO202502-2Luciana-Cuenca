package com.example.LosVehiculos.vehicle.repository;

import com.example.LosVehiculos.vehicle.entity.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VehicleRepository extends MongoRepository<Vehicle, Integer> {
}
