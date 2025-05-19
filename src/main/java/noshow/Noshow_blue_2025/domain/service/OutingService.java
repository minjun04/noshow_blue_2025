package noshow.Noshow_blue_2025.domain.service;

import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.domain.repositoryInterface.SeatRepository;
import noshow.Noshow_blue_2025.domain.repositoryInterface.StudentRepository;
import noshow.Noshow_blue_2025.infra.entity.Seat;
import noshow.Noshow_blue_2025.infra.entity.Student;
import org.apache.el.parser.BooleanNode;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OutingService {

    private final StudentRepository studentRepository;
    private final SeatRepository seatRepository;

    public Boolean checkSeatAssignment(Student student) {
        if (student.getSeatId() == null) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean handleBreakOrReturn(Student student, boolean isBreak) {

        Seat seat = seatRepository.findBySeatId(student.getSeatId());

        if (isBreak) {

            LocalDateTime now = LocalDateTime.now();

            seat.setStartOfBreakTime(now);
            if (now.plusMinutes(80).isAfter(now.plusMinutes(seat.getRemainingBreakTime()))){
                seat.setEndOfBreakTime(now.plusMinutes(seat.getRemainingBreakTime()));
            }
            else{
                seat.setEndOfBreakTime(now.plusMinutes(80));
            }
            seatRepository.save(seat);
            return true;
        } else {

            student.setSeatId(null);
            seat.setReserved(false); // 좌석 예약 해제

            seatRepository.save(seat);
            return false;
        }
    }
}
