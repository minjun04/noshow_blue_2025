package noshow.Noshow_blue_2025.api.controller;

import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.api.controller.dto.Reserve.ReserveSeatRequest;
import noshow.Noshow_blue_2025.domain.service.ReservationService;
import noshow.Noshow_blue_2025.infra.entity.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    // 좌석 예약 요청
    @PostMapping("/reserve")
    public ResponseEntity<Boolean> reserveSeat(
            @RequestBody ReserveSeatRequest reserveSeatRequest,
            @AuthenticationPrincipal Student student) {
        try {
            Boolean result = reservationService.reserveSeat(student.getStudentId(), reserveSeatRequest.getSeatId());

            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    // 좌석 연장 요청
    @PostMapping("/extend")
    public ResponseEntity<Boolean> extendReservation(
            @AuthenticationPrincipal Student student
    ) {
        try {
            Boolean result = reservationService.extendReservation(student.getStudentId());
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @PostMapping("/exit")
    public ResponseEntity<Boolean> Exit(
            @AuthenticationPrincipal Student student
    ) {
        try {
            Boolean result = reservationService.ExitSeat(student.getStudentId());
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(false);
        }
    }
    
    @GetMapping("/isReserved")
    public ResponseEntity<?> Reserved(@AuthenticationPrincipal Student student) {
        try {
            return ResponseEntity.ok(reservationService.isReserved(student));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        }
    }
}
