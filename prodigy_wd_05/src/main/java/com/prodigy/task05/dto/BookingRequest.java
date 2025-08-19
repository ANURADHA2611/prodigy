package com.prodigy.task05.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class BookingRequest {
    @NotNull public Long roomId;
    @NotNull public LocalDate checkIn;
    @NotNull public LocalDate checkOut;
}
