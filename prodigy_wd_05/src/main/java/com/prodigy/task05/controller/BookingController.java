package com.prodigy.task05.controller;

import com.prodigy.task05.dto.BookingRequest;
import com.prodigy.task05.model.Booking;
import com.prodigy.task05.model.User;
import com.prodigy.task05.repo.BookingRepository;
import com.prodigy.task05.repo.RoomRepository;
import com.prodigy.task05.repo.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired BookingRepository bookingRepository;
    @Autowired RoomRepository roomRepository;
    @Autowired UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody BookingRequest req, @AuthenticationPrincipal UserDetails current) {
        var room = roomRepository.findById(req.roomId).orElseThrow();
        // ensure availability
        var overlaps = bookingRepository.findOverlaps(room.getId(), req.checkIn, req.checkOut);
        if (!overlaps.isEmpty()) {
            return ResponseEntity.badRequest().body("Room not available for selected dates");
        }
        User user = userRepository.findByUsername(current.getUsername()).orElseThrow();
        Booking b = new Booking();
        b.setRoom(room);
        b.setUser(user);
        b.setCheckIn(req.checkIn);
        b.setCheckOut(req.checkOut);
        long nights = java.time.temporal.ChronoUnit.DAYS.between(req.checkIn, req.checkOut);
        b.setTotalPrice(nights * room.getPricePerNight());
        bookingRepository.save(b);
        return ResponseEntity.ok(b);
    }

    @GetMapping("/me")
    public List<Booking> myBookings(@AuthenticationPrincipal UserDetails current) {
        User u = userRepository.findByUsername(current.getUsername()).orElseThrow();
        return bookingRepository.findAll().stream().filter(b -> b.getUser().getId().equals(u.getId())).toList();
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable Long id, @AuthenticationPrincipal UserDetails current) {
        return bookingRepository.findById(id).map(b -> {
            if (!b.getUser().getUsername().equals(current.getUsername())) return ResponseEntity.status(403).build();
            b.setStatus("CANCELLED");
            bookingRepository.save(b);
            return ResponseEntity.ok(b);
        }).orElse(ResponseEntity.notFound().build());
    }
}
