package com.example.LosVehiculos.vehicle.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class VehicleRequestDto {

    @NotBlank(message = "La marca es obligatoria")
    private String brand;

    @NotBlank(message = "El modelo es obligatorio")
    private String model;

    @NotNull(message = "El año es obligatorio")
    @Min(value = 1886, message = "El año debe ser válido")
    private Integer year;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser positivo")
    private Double price;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {this.brand = brand;}

    public String getModel() {return model;}

    public void setModel(String model) {this.model = model;}

    public Integer getYear() {return year;}

    public void setYear(Integer year) {this.year = year;}

    public Double getPrice() {return price;}

    public void setPrice(Double price) {this.price = price;}
}
