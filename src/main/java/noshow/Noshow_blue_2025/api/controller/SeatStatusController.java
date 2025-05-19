package noshow.Noshow_blue_2025.api.controller;

import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.api.controller.dto.SeatStatus.OtherSeatStatusResponse;
import noshow.Noshow_blue_2025.api.controller.dto.SeatStatus.SeatStatusResponse;
import noshow.Noshow_blue_2025.domain.service.OtherUserSeatStatusService;
import noshow.Noshow_blue_2025.domain.service.ReservationService;
import noshow.Noshow_blue_2025.infra.entity.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seat")
public class SeatStatusController {

    private final ReservationService reservationService;
    private final OtherUserSeatStatusService otherUserSeatStatusService;

    @GetMapping("/status")
    public ResponseEntity<?> getSeatStatus(@AuthenticationPrincipal Student student) { //나의 좌석 상태
        ResponseEntity<?> response = reservationService.getSeatStatus(student);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/otherUserStatus")
    public ResponseEntity<?> getOtherSeatStatus(String seatId) {
        OtherSeatStatusResponse response = otherUserSeatStatusService.getOtherSeatStatus(seatId);
        return ResponseEntity.ok(response);
    }
}

