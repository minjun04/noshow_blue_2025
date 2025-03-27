package noshow.Noshow_blue_2025.domain.service;

import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.api.controller.dto.QrVerifyRequest;
import noshow.Noshow_blue_2025.api.controller.dto.QrVerifyResponse;
import noshow.Noshow_blue_2025.domain.repositoryInterface.StudentRepository;
import noshow.Noshow_blue_2025.infra.entity.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/qr")
public class QrService {

    private final StudentRepository studentRepository;

    @PostMapping("/verify")
    public ResponseEntity<QrVerifyResponse> verifyStudent(@RequestBody QrVerifyRequest qr) {

        // 예시: DB에서 학번으로 학생 조회
        Optional<Student> student = studentRepository.findById(qr.getStudentId());

        if (student.isPresent() && student.get().getName().equals(qr.getName())) {
            return ResponseEntity.ok(new QrVerifyResponse());//확인완료
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new QrVerifyResponse());//잘못된 저장
        }
    }
    public void QrVerifyResponse QrStudentCheck(){

    }
    public void QrVerifyRequest  QrStudentIdout(){

    }
}
