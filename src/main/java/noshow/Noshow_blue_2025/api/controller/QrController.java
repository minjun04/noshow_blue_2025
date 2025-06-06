package noshow.Noshow_blue_2025.api.controller;

import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.api.controller.dto.Outing.OutingRequest;
import noshow.Noshow_blue_2025.domain.service.QrService;
import noshow.Noshow_blue_2025.infra.entity.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/qr")
public class QrController {
    private final QrService qrService;

    @PostMapping("/info")
    public ResponseEntity<?> UpdateEntry(@AuthenticationPrincipal Student student) {
        try {
            return qrService.UpdateEntry(student);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        }
    }

    @GetMapping("/isReserved")
    public ResponseEntity<?> Reserved(@AuthenticationPrincipal Student student) {
        try {
            return ResponseEntity.ok(qrService.isReserved(student));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        }
    }

    @PostMapping("/break")
    public ResponseEntity<?> handleBreak(@RequestBody OutingRequest request) {
        Student student = (Student) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(request.isBreak());
        Boolean result = qrService.handleBreakOrReturn(student, request.isBreak());
        return ResponseEntity.ok(result);
    }
}