package com.example.LosVehiculos.vehicle.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vehicles")
public class Vehicle {

    public static final String SEQUENCE_NAME = "vehicle_sequence";

    @Id
    private Integer id;
    private String brand;
    private String model;
    private Integer year;
    private Double price;

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    public String getBrand() {return brand;}

    public void setBrand(String brand) {this.brand = brand;}

    public String getModel() {return model;}

    public void setModel(String model) {this.model = model;}

    public Integer getYear() {return year;}

    public void setYear(Integer year) {this.year = year;}

    public Double getPrice() {return price;}

    public void setPrice(Double price) {this.price = price;}
}
