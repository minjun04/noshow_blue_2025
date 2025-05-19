package noshow.Noshow_blue_2025.api.controller;

import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.api.controller.dto.SeatStatus.SeatStatusResponse;
import noshow.Noshow_blue_2025.domain.repositoryInterface.SeatRepository;
import noshow.Noshow_blue_2025.domain.repositoryInterface.StudentRepository;
import noshow.Noshow_blue_2025.domain.service.ReservationService;
import noshow.Noshow_blue_2025.infra.entity.Seat;
import noshow.Noshow_blue_2025.infra.entity.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class SeatStatusController {

    private final StudentRepository studentRepository;
    private final SeatRepository seatRepository;
    private final ReservationService reservationService;

    @GetMapping("/seat-status")
    public ResponseEntity<?> getSeatStatus(@AuthenticationPrincipal Student student) {
        Optional<Student> optionalStudent = studentRepository.findById(student.getStudentId());
        if (optionalStudent.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("학생을 찾을 수 없습니다.");
        }

        Seat seat = seatRepository.findBySeatId(student.getSeatId());

        if (seat.getSeatId() == null) {
            return ResponseEntity.ok("현재 예약된 좌석이 없습니다.");
        }

        long remainingMinutes = reservationService.getRemainingMinutes(student.getStudentId());

        SeatStatusResponse response = new SeatStatusResponse(
                student.getStudentId(),
                student.getSeatId(),
                remainingMinutes,
                seat.getNumOfExtensions()
        );

        return ResponseEntity.ok(response);
    }
}

