package noshow.Noshow_blue_2025.domain.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.api.controller.dto.SeatStatus.SeatStatusResponse;
import noshow.Noshow_blue_2025.domain.repositoryInterface.SeatRepository;
import noshow.Noshow_blue_2025.domain.repositoryInterface.StudentRepository;
import noshow.Noshow_blue_2025.infra.entity.Seat;
import noshow.Noshow_blue_2025.infra.entity.Student;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final StudentRepository studentRepository;
    private final SeatRepository seatRepository;
    // 기본 예약 시간: 3시간
    private static final Duration BASE_DURATION = Duration.ofHours(3);
    // 연장 시 추가 시간: 2시간
    private static final Duration EXTENSION_DURATION = Duration.ofHours(2);
    // 최대 연장 가능 횟수
    private static final int MAX_EXTENSIONS = 3;

    @Transactional
    public Boolean reserveSeat(String studentId, String seatId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("학생을 찾을 수 없습니다."));
        Seat seat = seatRepository.findBySeatId(student.getSeatId());

        seat.setSeatId(seatId);
        seat.setStartOfReservation(LocalDateTime.now());
        seat.setEndOfReservation(LocalDateTime.now().plus(BASE_DURATION));
        seat.setNumOfExtensions(0);

        studentRepository.save(student);
        return true;
    }

    @Transactional
    public Boolean extendReservation(String studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("학생을 찾을 수 없습니다."));
        Seat seat = seatRepository.findBySeatId(student.getSeatId());

        if (seat.getNumOfExtensions() >= MAX_EXTENSIONS) {
            return false;
        }

        seat.setEndOfReservation(seat.getEndOfReservation().plus(EXTENSION_DURATION));
        seat.setNumOfExtensions(seat.getNumOfExtensions() + 1);

        studentRepository.save(student);
        return true;
    }

    //남은 시간 계산
    public long getRemainingMinutes(String studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("학생을 찾을 수 없습니다."));
        Seat seat = seatRepository.findBySeatId(student.getSeatId());

        if (seat.getEndOfReservation() == null) return 0;
        return Duration.between(LocalDateTime.now(), seat.getEndOfReservation()).toMinutes();
    }

    public SeatStatusResponse getSeatStatus(Student student) {
        // 좌석 정보 조회
        Seat seat = seatRepository.findBySeatId(student.getSeatId());

        // 좌석이 없으면 메시지 대신 null을 반환하거나 예외 처리
        if (seat == null || seat.getSeatId() == null) {
            // 필요 시 예외 던지거나 null 리턴 (컨트롤러에서 예외처리)
            return new SeatStatusResponse(
                    student.getStudentId(),
                    null,
                    0L,
                    0
            );
        }

        long remainingMinutes = getRemainingMinutes(student.getStudentId());

        return new SeatStatusResponse(
                student.getStudentId(),
                student.getSeatId(),
                remainingMinutes,
                seat.getNumOfExtensions()
        );
    }
    public void updateRemainingBreakTime(Student student){
        Seat seat = seatRepository.findBySeatId(student.getSeatId());

        LocalDateTime startOfBreakTime = seat.getStartOfBreakTime();
        LocalDateTime now = LocalDateTime.now();
        long elapsedMinutes = Duration.between(startOfBreakTime, now).toMinutes();

        long remaining = seat.getRemainingBreakTime() - elapsedMinutes;

        seat.setRemainingBreakTime(remaining);
        seatRepository.save(seat);
    }
}


