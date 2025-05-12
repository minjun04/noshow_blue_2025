package noshow.Noshow_blue_2025.domain.service;

import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.api.controller.dto.Qr.QrVerifyRequest;
import noshow.Noshow_blue_2025.api.controller.dto.Qr.QrVerifyResponse;
import noshow.Noshow_blue_2025.domain.repositoryInterface.StudentRepository;
import noshow.Noshow_blue_2025.infra.entity.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class QrService {

    private final StudentRepository studentRepository;

    // DB에서 학번 및 이름 으로 학생 조회
    public ResponseEntity<QrVerifyResponse> verifyStudent(@RequestBody QrVerifyRequest qrRequest) {

        Student student = studentRepository.findByIdAndName(qrRequest.getStudentId(),qrRequest.getName());

        if (student!=null && student.getName().equals(qrRequest.getName())&&student.getStudentId().equals(qrRequest.getStudentId())) {
            return ResponseEntity.ok(QrVerifyResponse.from(student)); //확인완료
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);//잘못된 저장
        }
    }
}
