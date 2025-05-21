package noshow.Noshow_blue_2025.domain.repositoryInterface;

import noshow.Noshow_blue_2025.infra.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, String> {
    Seat findBySeatId(String seatId);

    // 예약된 좌석만 뽑아올수 있도록 처리
    @Query("SELECT s FROM Seat s WHERE s.reserved = true AND s.endOfReservation > CURRENT_TIMESTAMP")
    List<Seat> findAllReservedSeats();
}
