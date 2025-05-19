package noshow.Noshow_blue_2025.api.controller;

import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.api.controller.dto.SeatStatusResponse;
import noshow.Noshow_blue_2025.domain.repositoryInterface.StudentRepository;
import noshow.Noshow_blue_2025.infra.entity.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class SeatStatusController {

    private final StudentRepository studentRepository;

    @GetMapping("/seat-status")
    public ResponseEntity<?> getSeatStatus(@RequestParam String studentId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("학생을 찾을 수 없습니다.");
        }

        Student student = optionalStudent.get();

        if (student.getSeatId() == null) {
            return ResponseEntity.ok("현재 예약된 좌석이 없습니다.");
        }

        long remainingMinutes = student.getRemainingMinutes();

        SeatStatusResponse response = new SeatStatusResponse(
                student.getStudentId(),
                student.getSeatId(),
                remainingMinutes,
                student.getNumOfExtensions()
        );

        return ResponseEntity.ok(response);
    }
}

