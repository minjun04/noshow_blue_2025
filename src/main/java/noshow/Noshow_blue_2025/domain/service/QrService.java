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
public class QrService {
    private final StudentRepository studentRepository;
    private final SeatRepository seatRepository;

    // 이메일로 학생 조회 + entry 값 변경
    public Student findAndUpdateEntryByEmail(String email) {
        Student student1 = studentRepository.findByEmail(email);
        if (student1 == null) {
            throw new IllegalArgumentException("Student not found: " + email);
        }

        if(student1.getEntry() != 1){

        }
        Student student = student1;
        student.setEntry(1);
        return studentRepository.save(student1);  // entry 변경 후 저장
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
