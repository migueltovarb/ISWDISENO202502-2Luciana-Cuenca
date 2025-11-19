package com.example.LosVehiculos.vehicle.mapper;

import com.example.LosVehiculos.vehicle.dto.VehicleRequestDto;
import com.example.LosVehiculos.vehicle.dto.VehicleResponseDto;
import com.example.LosVehiculos.vehicle.entity.Vehicle;

public final class VehicleMapper {

    private VehicleMapper() {
    }

    public static Vehicle toEntity(VehicleRequestDto dto) {
        Vehicle vehicle = new Vehicle();
        updateEntity(vehicle, dto);
        return vehicle;
    }

    public static void updateEntity(Vehicle vehicle, VehicleRequestDto dto) {
        vehicle.setBrand(dto.getBrand());
        vehicle.setModel(dto.getModel());
        vehicle.setYear(dto.getYear());
        vehicle.setPrice(dto.getPrice());
    }

    public static VehicleResponseDto toResponseDto(Vehicle vehicle) {
        VehicleResponseDto responseDto = new VehicleResponseDto();
        responseDto.setId(vehicle.getId());
        responseDto.setBrand(vehicle.getBrand());
        responseDto.setModel(vehicle.getModel());
        responseDto.setYear(vehicle.getYear());
        responseDto.setPrice(vehicle.getPrice());
        return responseDto;
    }
}
