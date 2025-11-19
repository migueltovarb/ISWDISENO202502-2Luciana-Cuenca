package com.example.LosVehiculos.vehicle.exception;

public class VehicleNotFoundException extends RuntimeException {

    public VehicleNotFoundException(Integer id) {
        super("Vehículo con id %d no encontrado".formatted(id));
    }
}
