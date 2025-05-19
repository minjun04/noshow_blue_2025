package noshow.Noshow_blue_2025.api.controller;

import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.api.controller.dto.Outing.OutingRequest;
import noshow.Noshow_blue_2025.api.controller.dto.Qr.QrRequest;
import noshow.Noshow_blue_2025.api.controller.dto.Qr.QrResponse;
import noshow.Noshow_blue_2025.domain.service.QrService;
import noshow.Noshow_blue_2025.infra.entity.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/qr")
public class QrController {
    private final QrService qrService;

    @PostMapping("/info")
    public ResponseEntity<?> getUserInfoAndUpdateEntry(@RequestBody QrRequest request) {
        try {
            Student student = qrService.findAndUpdateEntryByEmail(request.getEmail());

            return ResponseEntity.ok(new QrResponse(
                    student.getName(),
                    student.getStudentId(),
                    student.getEmail(),
                    student.getEntry()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        }
    }
    @PostMapping("/break")
    public ResponseEntity<?> handleBreak(@RequestBody OutingRequest request) {
        Student student = (Student) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Boolean result = qrService.handleBreakOrReturn(student, request.isBreak());
        return ResponseEntity.ok(result);
    }
}