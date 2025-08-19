package com.prodigy.task05.repo;
import com.prodigy.task05.model.Booking;
import com.prodigy.task05.model.Room;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("select b from Booking b where b.room.id = :roomId and b.status = 'CONFIRMED' and ( (b.checkIn <= :checkout and b.checkOut >= :checkin) )")
    List<Booking> findOverlaps(@Param("roomId") Long roomId, @Param("checkin") LocalDate checkin, @Param("checkout") LocalDate checkout);
}
