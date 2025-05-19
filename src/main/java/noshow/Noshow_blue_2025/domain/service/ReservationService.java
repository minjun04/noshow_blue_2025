package noshow.Noshow_blue_2025.domain.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.domain.repositoryInterface.StudentRepository;
import noshow.Noshow_blue_2025.infra.entity.Student;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final StudentRepository studentRepository;

    // 기본 예약 시간: 3시간
    private static final Duration BASE_DURATION = Duration.ofHours(3);
    // 연장 시 추가 시간: 2시간
    private static final Duration EXTENSION_DURATION = Duration.ofHours(2);
    // 최대 연장 가능 횟수
    private static final int MAX_EXTENSIONS = 3;

    @Transactional
    public String reserveSeat(String studentId, String seatId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("학생을 찾을 수 없습니다."));

        student.setSeatId(seatId);
        student.setStartOfReservation(LocalDateTime.now());
        student.setEndOfReservation(LocalDateTime.now().plus(BASE_DURATION));
        student.setNumOfExtensions(0);

        studentRepository.save(student);
        return "좌석 예약이 완료되었습니다. (3시간)";
    }

    @Transactional
    public String extendReservation(String studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("학생을 찾을 수 없습니다."));

        if (student.getNumOfExtensions() >= MAX_EXTENSIONS) {
            return "연장 가능 횟수를 모두 사용하였습니다.";
        }

        student.setEndOfReservation(student.getEndOfReservation().plus(EXTENSION_DURATION));
        student.setNumOfExtensions(student.getNumOfExtensions() + 1);

        studentRepository.save(student);
        return "예약이 2시간 연장되었습니다. 연장 횟수(" + student.getNumOfExtensions()+"/"+(3-student.getNumOfExtensions())+")";
    }

}

