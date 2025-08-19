package com.prodigy.task05.controller;

import com.prodigy.task05.model.Room;
import com.prodigy.task05.model.User;
import com.prodigy.task05.repo.BookingRepository;
import com.prodigy.task05.repo.RoomRepository;
import com.prodigy.task05.repo.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired RoomRepository roomRepository;
    @Autowired UserRepository userRepository;
    @Autowired BookingRepository bookingRepository;

    @PostMapping
    public ResponseEntity<Room> create(@Valid @RequestBody Room room, @AuthenticationPrincipal UserDetails current) {
        User owner = userRepository.findByUsername(current.getUsername()).orElseThrow();
        room.setOwner(owner);
        return ResponseEntity.ok(roomRepository.save(room));
    }

    @GetMapping
    public List<Room> all() { return roomRepository.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Room> one(@PathVariable Long id) {
        return roomRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> update(@PathVariable Long id, @Valid @RequestBody Room updated, @AuthenticationPrincipal UserDetails current) {
        return roomRepository.findById(id).map(room -> {
            if (!room.getOwner().getUsername().equals(current.getUsername())) return ResponseEntity.status(403).build();
            room.setName(updated.getName());
            room.setType(updated.getType());
            room.setCapacity(updated.getCapacity());
            room.setPricePerNight(updated.getPricePerNight());
            return ResponseEntity.ok(roomRepository.save(room));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails current) {
        return roomRepository.findById(id).map(room -> {
            if (!room.getOwner().getUsername().equals(current.getUsername())) return ResponseEntity.status(403).build();
            roomRepository.delete(room);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    // Search available rooms by date range
    @GetMapping("/search")
    public List<Room> search(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut) {

        List<Room> rooms = roomRepository.findAll();
        return rooms.stream().filter(room ->
                bookingRepository.findOverlaps(room.getId(), checkIn, checkOut).isEmpty()
        ).collect(Collectors.toList());
    }
}
