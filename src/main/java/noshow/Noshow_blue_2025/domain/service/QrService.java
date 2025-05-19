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
    public Student findAndUpdateEntryByEmail(String email) {
        Student student = studentRepository.findByEmail(email);
        if (student == null) {
            throw new IllegalArgumentException("Student not found: " + email);
        }

        if(student.getEntry()==0) {
            student.setEntry(1);
            return studentRepository.save(student);  // entry 변경 후 저장
        }
        else if(student.getEntry()==1){

            //앱으로 부터 요청을 보내야 함.
            return 1;
        }
        else{

        }
    }
}
