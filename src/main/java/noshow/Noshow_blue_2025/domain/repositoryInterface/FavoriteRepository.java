package noshow.Noshow_blue_2025.domain.repositoryInterface;

import noshow.Noshow_blue_2025.infra.entity.Favorite;
import noshow.Noshow_blue_2025.infra.entity.Seat;
import noshow.Noshow_blue_2025.infra.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByStudent(Student student);
    boolean existsByStudentAndSeat(Student student, Seat seat);
    Optional<Favorite> findByStudentAndSeat(Student student, Seat seat);
    List<Favorite> findBySeat_SeatId(String seatId);
}
