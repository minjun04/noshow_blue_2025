package noshow.Noshow_blue_2025.domain.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.api.controller.dto.SeatStatus.SeatStatusResponse;
import noshow.Noshow_blue_2025.domain.repositoryInterface.SeatRepository;
import noshow.Noshow_blue_2025.domain.repositoryInterface.StudentRepository;
import noshow.Noshow_blue_2025.infra.entity.Seat;
import noshow.Noshow_blue_2025.infra.entity.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final StudentRepository studentRepository;
    private final SeatRepository seatRepository;
    private final FavoriteService favoriteService;
    // 기본 예약 시간: 3시간
    private static final Duration BASE_DURATION = Duration.ofHours(3);
    // 연장 시 추가 시간: 2시간
    private static final Duration EXTENSION_DURATION = Duration.ofHours(4);
    // 최대 연장 가능 횟수
    private static final int MAX_EXTENSIONS = 3;

    @Transactional
    public Boolean reserveSeat(String studentId, String seatId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("학생을 찾을 수 없습니다."));
        if(student.getEntry()!=1){
            return false;

        }

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

        if(student.getEntry()!=1){
            return false;
        }

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

        if (seat.getEndOfReservation() == null) {
            return 0;
        }
        return Duration.between(LocalDateTime.now(), seat.getEndOfReservation()).toMinutes();
    }

    public ResponseEntity<?> getSeatStatus(Student student) {

        if (student.getEntry()==0 || student.getSeatId() == null) {
            // 좌석 ID가 없으면 즉시 204 No Content
            return ResponseEntity.noContent().build();
        }
        // 좌석 정보 조회
        Seat seat = seatRepository.findBySeatId(student.getSeatId());

        long remainingMinutes = getRemainingMinutes(student.getStudentId());

        //예약시간 초과시 강제 퇴실
        LocalDateTime now =LocalDateTime.now();
        if(now.isAfter(seat.getEndOfReservation())){
            ExitSeat(student.getStudentId());
        }

        //외출시간 초과시 강제퇴실
        if(student.getEntry()==-1 && now.isAfter(seat.getEndOfBreakTime())){
            ExitSeat(student.getStudentId());
        }

        SeatStatusResponse response = new SeatStatusResponse(
                student.getStudentId(),
                student.getSeatId(),
                remainingMinutes,
                seat.getNumOfExtensions()
        );

        return ResponseEntity.ok(response);
    }

    @Transactional
    public Boolean ExitSeat(String studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("학생을 찾을 수 없습니다."));
        String seatId = student.getSeatId();

        if (seatId != null) {
            student.setSeatId(null);
            student.setEntry(0); // 퇴실
            studentRepository.save(student);

            Seat seat = seatRepository.findById(seatId).orElseThrow();
            seat.setReserved(false);
            seat.setStartOfReservation(null);
            seat.setEndOfReservation(null);
            seat.setStartOfBreakTime(null);
            seat.setEndOfBreakTime(null);
            seat.setNumOfExtensions(0);
            seatRepository.save(seat);

            favoriteService.sendFcmToStudent(student, seatId);
        }
        return true;
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

    public Boolean isReserved(Student student) {
        if (student.getSeatId() == null){
            return false;
        }
        return true;
    }
}


