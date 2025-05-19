package noshow.Noshow_blue_2025.api.controller;

import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.api.controller.dto.ReserveSeatRequest;
import noshow.Noshow_blue_2025.domain.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    // 좌석 예약 요청
    @PostMapping("/reserve")
    public ResponseEntity<Boolean> reserveSeat(@RequestBody ReserveSeatRequest reserveSeatRequest) {
        try {
            Boolean result = reservationService.reserveSeat(reserveSeatRequest.getStudentId(), reserveSeatRequest.getSeatId());
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    // 좌석 연장 요청
    @PostMapping("/extend")
    public ResponseEntity<Boolean> extendReservation(@RequestBody ReserveSeatRequest reserveSeatRequest) {
        try {
            Boolean result = reservationService.extendReservation(reserveSeatRequest.getStudentId());
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(false);
        }
    }
}
