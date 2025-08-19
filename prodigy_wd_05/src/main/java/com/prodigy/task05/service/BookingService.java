package com.prodigy.task05.service;

import com.prodigy.task05.model.Booking;
import com.prodigy.task05.model.Room;
import com.prodigy.task05.repo.BookingRepository;
import com.prodigy.task05.repo.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BookingService {

    @Autowired BookingRepository bookingRepository;
    @Autowired RoomRepository roomRepository;

    @Transactional
    public Booking createBooking(Long roomId, Long userId, LocalDate checkIn, LocalDate checkOut, double pricePerNight) {
        if (!checkOut.isAfter(checkIn)) {
            throw new IllegalArgumentException("Check-out must be after check-in");
        }
        // ensure availability
        List<Booking> overlaps = bookingRepository.findOverlaps(roomId, checkIn, checkOut);
        if (!overlaps.isEmpty()) {
            throw new IllegalStateException("Room not available for selected dates");
        }
        Room room = roomRepository.findById(roomId).orElseThrow();
        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        double total = nights * room.getPricePerNight();
        Booking b = new Booking();
        b.setRoom(room);
        // user will be assigned in controller via repository reference to avoid fetching user here
        b.setCheckIn(checkIn);
        b.setCheckOut(checkOut);
        b.setTotalPrice(total);
        return bookingRepository.save(b);
    }
}
