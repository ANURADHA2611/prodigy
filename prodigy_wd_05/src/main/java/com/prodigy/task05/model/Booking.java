package com.prodigy.task05.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Booking {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Room room;

    @ManyToOne(optional = false)
    private User user;

    @NotNull @FutureOrPresent
    private LocalDate checkIn;

    @NotNull @FutureOrPresent
    private LocalDate checkOut;

    private Double totalPrice;

    private String status = "CONFIRMED"; // CONFIRMED, CANCELLED

    public Long getId() {return id;}
    public Room getRoom() {return room;}
    public void setRoom(Room room) {this.room = room;}
    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}
    public LocalDate getCheckIn() {return checkIn;}
    public void setCheckIn(LocalDate checkIn) {this.checkIn = checkIn;}
    public LocalDate getCheckOut() {return checkOut;}
    public void setCheckOut(LocalDate checkOut) {this.checkOut = checkOut;}
    public Double getTotalPrice() {return totalPrice;}
    public void setTotalPrice(Double totalPrice) {this.totalPrice = totalPrice;}
    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}
}
