package noshow.Noshow_blue_2025.domain.service;

import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.domain.repositoryInterface.SeatRepository;
import noshow.Noshow_blue_2025.domain.repositoryInterface.StudentRepository;
import noshow.Noshow_blue_2025.infra.entity.Seat;
import noshow.Noshow_blue_2025.infra.entity.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QrService {
    private final StudentRepository studentRepository;
    private final SeatRepository seatRepository;
    private final ReservationService reservationService;

    // 이메일로 학생 조회 + entry 값 변경
    public ResponseEntity<Student> findAndUpdateEntryByEmail(Student student) {

        if (student == null) {
            throw new IllegalArgumentException("Student not found: ");
        }

        Student beforeStudent = Student.builder()
                .name(student.getName())
                .email(student.getEmail())
                .studentId(student.getStudentId())
                .seatId(student.getSeatId())
                .entry(student.getEntry())
                .build();

        if(student.getEntry() != 1) {
            if (student.getEntry() == 0){
                student.setEntry(1);
            }
            else{
                reservationService.updateRemainingBreakTime(student);
                student.setEntry(1);
            }
        }
        studentRepository.save(student);
        return ResponseEntity.ok(studentRepository.save(beforeStudent));
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
            student.setEntry(-1);
            studentRepository.save(student);
            seatRepository.save(seat);
        } else {

            student.setSeatId(null);
            student.setEntry(0);
            seat.setReserved(false); // 좌석 예약 해제

            seatRepository.save(seat);
            studentRepository.save(student);
        }
        return true;
    }
}
