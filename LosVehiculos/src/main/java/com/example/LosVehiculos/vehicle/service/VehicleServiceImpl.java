package com.example.LosVehiculos.vehicle.service;

import com.example.LosVehiculos.common.persistence.SequenceGeneratorService;
import com.example.LosVehiculos.vehicle.dto.VehicleRequestDto;
import com.example.LosVehiculos.vehicle.dto.VehicleResponseDto;
import com.example.LosVehiculos.vehicle.entity.Vehicle;
import com.example.LosVehiculos.vehicle.exception.VehicleNotFoundException;
import com.example.LosVehiculos.vehicle.mapper.VehicleMapper;
import com.example.LosVehiculos.vehicle.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    public VehicleServiceImpl(VehicleRepository vehicleRepository,
                              SequenceGeneratorService sequenceGeneratorService) {
        this.vehicleRepository = vehicleRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public VehicleResponseDto createVehicle(VehicleRequestDto requestDto) {
        Vehicle vehicle = VehicleMapper.toEntity(requestDto);
        vehicle.setId(sequenceGeneratorService.generateSequence(Vehicle.SEQUENCE_NAME));
        Vehicle saved = vehicleRepository.save(vehicle);
        return VehicleMapper.toResponseDto(saved);
    }

    @Override
    public List<VehicleResponseDto> getAllVehicles() {
        return vehicleRepository.findAll()
                .stream()
                .map(VehicleMapper::toResponseDto)
                .toList();
    }

    @Override
    public VehicleResponseDto getVehicleById(Integer id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException(id));
        return VehicleMapper.toResponseDto(vehicle);
    }

    @Override
    public VehicleResponseDto updateVehicle(Integer id, VehicleRequestDto requestDto) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException(id));
        VehicleMapper.updateEntity(vehicle, requestDto);
        Vehicle updated = vehicleRepository.save(vehicle);
        return VehicleMapper.toResponseDto(updated);
    }

    @Override
    public void deleteVehicle(Integer id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException(id));
        vehicleRepository.delete(vehicle);
    }
}
