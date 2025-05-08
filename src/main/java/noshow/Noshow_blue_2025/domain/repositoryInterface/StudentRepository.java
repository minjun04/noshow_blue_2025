package noshow.Noshow_blue_2025.domain.repositoryInterface;

import noshow.Noshow_blue_2025.infra.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
    Student findByEmail(String email);
    Student findByStudentId(String studentId);
    Student findByStudentIdAndName(String studentId,String name);
//    Student findByEmailAndPassword(String studentLoginId, String studentPassword);
}
