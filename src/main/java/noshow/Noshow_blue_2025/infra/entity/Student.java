package noshow.Noshow_blue_2025.infra.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Student {
    @Id
    @Column(name="studentId",nullable = false)
    private String studentId;
    private String name;
    private String email;
    private String seatId;
    private Integer entry;
    private Integer remainingBreakTime; // 분 단위, 초기값: 210
    private LocalDateTime startOfBreakTime;

    //예약
    private LocalDateTime endOfReservation;
    private LocalDateTime startOfReservation;
    private int numOfExtensions; // 연장 횟수

    @Builder
    private Student(String name, String email, String studentId, String seatId, Integer entry) {
        this.name = name;
        this.email = email;
        this.studentId = studentId;
        this.seatId = seatId;
        this.entry = entry;
    }

    //남은 시간 계산
    public long getRemainingMinutes() {
        if (endOfReservation == null) return 0;
        return Duration.between(LocalDateTime.now(), endOfReservation).toMinutes();
    }
}
