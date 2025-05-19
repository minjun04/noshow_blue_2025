package noshow.Noshow_blue_2025.domain.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.domain.repositoryInterface.StudentRepository;
import noshow.Noshow_blue_2025.infra.entity.Student;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BreakTimerService {

    private final StudentRepository studentRepository;

    // 3시간 30분 제한
    private static final Duration MAX_BREAK_DURATION = Duration.ofMinutes(210);

    @Transactional
    public void checkBreakTimeAndAutoExit() {
        List<Student> students = studentRepository.findAll();

        for (Student student : students) {
            if (student.getStartOfBreakTime() != null && student.getRemainingBreakTime() != null) {
                long elapsedMinutes = Duration.between(student.getStartOfBreakTime(), LocalDateTime.now()).toMinutes();

                int updatedRemaining = student.getRemainingBreakTime() - (int) elapsedMinutes;
                updatedRemaining = Math.max(updatedRemaining, 0);

                student.setRemainingBreakTime(updatedRemaining);
                student.setStartOfBreakTime(LocalDateTime.now()); // reset 기준시간

                // 남은 시간이 0 이하면 자동 퇴실
                if (updatedRemaining <= 0) {
                    student.setSeatId(null); // 자리 해제
                    student.setStartOfBreakTime(null);
                    student.setEndOfReservation(LocalDateTime.now());
                    System.out.println("[AUTO EXIT] " + student.getName() + "님이 자동 퇴실 처리되었습니다.");
                }

                studentRepository.save(student);
            }
        }
    }
}
