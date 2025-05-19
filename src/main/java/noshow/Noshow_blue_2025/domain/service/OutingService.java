package noshow.Noshow_blue_2025.domain.service;

import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.domain.repositoryInterface.SeatRepository;
import noshow.Noshow_blue_2025.domain.repositoryInterface.StudentRepository;
import noshow.Noshow_blue_2025.infra.entity.Seat;
import noshow.Noshow_blue_2025.infra.entity.Student;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OutingService {

    private final StudentRepository studentRepository;
    private final SeatRepository seatRepository;

    public String checkSeatAssignment(Student student) {
        if (student.getSeatId() == null) {
            return "좌석 미배정 상태입니다.";
        } else {
            return "좌석이 배정되어 있습니다.";
        }
    }

    public String handleBreakOrReturn(Student student, boolean isBreak) {
        if (student.getSeatId() == null) {
            return "좌석 미배정 상태입니다.";
        }
        Seat seat = seatRepository.findBySeatId(student.getSeatId());
        if (isBreak) {

            LocalDateTime now = LocalDateTime.now();

            seat.setStartOfBreakTime(now);
            seat.setEndOfBreakTime(now.plusMinutes(90));

            seatRepository.save(seat);
            return "휴식 시작 처리 완료 ";
        } else {

            student.setSeatId(null);
            seat.setReserved(false); // 좌석 예약 해제

            seatRepository.save(seat);
            return "자리 반납 처리 완료";
        }
    }
}
