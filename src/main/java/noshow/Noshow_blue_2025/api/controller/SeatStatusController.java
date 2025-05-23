package noshow.Noshow_blue_2025.api.controller;

import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.api.controller.dto.SeatStatus.SeatStatusResponse;
import noshow.Noshow_blue_2025.domain.service.SeatStatusService;
import noshow.Noshow_blue_2025.domain.service.ReservationService;
import noshow.Noshow_blue_2025.domain.service.SeatCountService;
import noshow.Noshow_blue_2025.infra.entity.Seat;
import noshow.Noshow_blue_2025.infra.entity.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seat")
public class SeatStatusController {

    private final ReservationService reservationService;
    private final SeatStatusService SeatStatusService;
    private final SeatCountService seatCountService;

    @GetMapping("/status")
    public ResponseEntity<?> getSeatStatus(@AuthenticationPrincipal Student student) { //나의 좌석 상태
        ResponseEntity<?> response = reservationService.getSeatStatus(student);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/otherUserStatus")
    public ResponseEntity<?> getOtherSeatStatus(String seatId) {
        SeatStatusResponse response = SeatStatusService.getOtherSeatStatus(seatId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/seatcount")
    public int seatCount(){
        return seatCountService.getSeatCount();
    }

    @GetMapping("/reserved")
    public ResponseEntity<List<String>> getReservedSeat(){
        List<String> response = SeatStatusService.getReservedSeat();
        return ResponseEntity.ok(response);
    }
}

