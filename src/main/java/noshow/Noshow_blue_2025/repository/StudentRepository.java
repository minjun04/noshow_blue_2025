package noshow.Noshow_blue_2025.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByEmail(String Email);

    Student findByEmailAndPassword(String studentLoginId, String studentPassword);
}
