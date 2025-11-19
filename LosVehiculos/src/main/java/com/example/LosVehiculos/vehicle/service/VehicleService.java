package com.example.LosVehiculos.vehicle.service;

import com.example.LosVehiculos.vehicle.dto.VehicleRequestDto;
import com.example.LosVehiculos.vehicle.dto.VehicleResponseDto;

import java.util.List;

public interface VehicleService {

    VehicleResponseDto createVehicle(VehicleRequestDto requestDto);

    List<VehicleResponseDto> getAllVehicles();

    VehicleResponseDto getVehicleById(Integer id);

    VehicleResponseDto updateVehicle(Integer id, VehicleRequestDto requestDto);

    void deleteVehicle(Integer id);
}
