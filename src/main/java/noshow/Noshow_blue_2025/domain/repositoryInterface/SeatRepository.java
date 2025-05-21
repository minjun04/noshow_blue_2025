package noshow.Noshow_blue_2025.domain.repositoryInterface;

import noshow.Noshow_blue_2025.infra.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, String> {
    Seat findBySeatId(String seatId);

    @Query("SELECT s FROM Seat s " +
            "WHERE s.reserved = true " +
            "ORDER BY s.numOfExtensions DESC, s.endOfReservation ASC")
    List<Seat> findTop5ByNumOfExtensions(org.springframework.data.domain.Pageable pageable);

    @Query("SELECT s FROM Seat s " +
            "WHERE s.reserved = true " +
            "ORDER BY s.endOfReservation ASC")
    List<Seat> findTop5ByRemainingTime(org.springframework.data.domain.Pageable pageable);

    @Query("SELECT COUNT(s) FROM Seat s" +
            "WHERE s.reserved = false")
    int findSeatCount();
}
