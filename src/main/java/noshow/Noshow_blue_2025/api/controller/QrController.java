package noshow.Noshow_blue_2025.api.controller;

import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.api.controller.dto.Qr.QrResponse;
import noshow.Noshow_blue_2025.domain.service.QrService;
import noshow.Noshow_blue_2025.infra.entity.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class QrController {
    private final QrService qrService;

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@RequestParam String email) {
        try {
            Student student = qrService.findStudentByEmail(email);
            return ResponseEntity.ok(new QrResponse(
                    student.getName(),
                    student.getStudentId(),
                    student.getEmail()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        }


    }

}