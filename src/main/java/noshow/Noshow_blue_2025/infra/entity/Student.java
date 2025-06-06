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
    private String fcmToken;

    @Builder
    private Student(String name, String email, String studentId, String seatId, Integer entry) {
        this.name = name;
        this.email = email;
        this.studentId = studentId;
        this.seatId = seatId;
        this.entry = entry; // -1: 외출, 0: 퇴장, 1: 입장
    }

}
