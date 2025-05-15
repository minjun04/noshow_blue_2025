package noshow.Noshow_blue_2025.domain.service;

import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.domain.repositoryInterface.StudentRepository;
import noshow.Noshow_blue_2025.infra.entity.Student;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QrService {
    private final StudentRepository studentRepository;

    // 이메일로 학생 조회 + entry 값 변경
    public Student findAndUpdateEntryByEmail(String email, int newEntryValue) {
        Student student = studentRepository.findByEmail(email);
        if (student == null) {
            throw new IllegalArgumentException("Student not found: " + email);
        }

        student.setEntry(newEntryValue);
        return studentRepository.save(student);  // entry 변경 후 저장
    }
}
