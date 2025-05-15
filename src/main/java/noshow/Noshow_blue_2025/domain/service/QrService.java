package noshow.Noshow_blue_2025.domain.service;

import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.domain.repositoryInterface.StudentRepository;
import noshow.Noshow_blue_2025.infra.entity.Student;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QrService {
    private final StudentRepository studentRepository;

    // 이메일로 학생 찾기
    public Student findStudentByEmail(String email) {
        Student student = studentRepository.findByEmail(email);
        if (student == null) {
            throw new IllegalArgumentException("Student not found: " + email);
        }
        return student;
    }

    // studentId로 entry 값 변경
    public Student updateEntry(String studentId, int newEntryValue) {
        Student student = studentRepository.findById(studentId).orElse(null);

        if (student == null) {
            throw new IllegalArgumentException("Student not found: " + studentId);
        }

        student.setEntry(newEntryValue);
        return studentRepository.save(student);  // 변경 후 저장
    }
}
