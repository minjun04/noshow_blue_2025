package noshow.Noshow_blue_2025.api.controller;

import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.api.controller.dto.Qr.QrResponse;
import noshow.Noshow_blue_2025.domain.repositoryInterface.StudentRepository;
import noshow.Noshow_blue_2025.infra.entity.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class QrController {

    private final StudentRepository studentRepository ;

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@RequestParam String email) {
        Student student = studentRepository.findByEmail(email);
        if (student != null) {
            return ResponseEntity.ok(new QrResponse(
                    student.getName(), student.getStudentId(),student.getEmail()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다."); //예외 상황은 안나오게 만들었지만 예외 코드
        }
    }

}