package com.prodigy.task05.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Room {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String type; // e.g., Deluxe, Suite

    @NotNull @Min(1)
    private Integer capacity;

    @NotNull @Min(1)
    private Double pricePerNight;

    @ManyToOne
    private User owner; // the lister

    public Long getId() {return id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getType() {return type;}
    public void setType(String type) {this.type = type;}
    public Integer getCapacity() {return capacity;}
    public void setCapacity(Integer capacity) {this.capacity = capacity;}
    public Double getPricePerNight() {return pricePerNight;}
    public void setPricePerNight(Double pricePerNight) {this.pricePerNight = pricePerNight;}
    public User getOwner() {return owner;}
    public void setOwner(User owner) {this.owner = owner;}
}
