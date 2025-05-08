package noshow.Noshow_blue_2025.domain.repositoryInterface;

import org.springframework.data.jpa.repository.JpaRepository;
import noshow.Noshow_blue_2025.infra.entity.Student;

public interface QrRepository extends JpaRepository<Student, Long> {
    Student findByEmail(String email);
}
