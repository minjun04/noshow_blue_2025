package noshow.Noshow_blue_2025.domain.repositoryInterface;

import noshow.Noshow_blue_2025.infra.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, String> {
}
