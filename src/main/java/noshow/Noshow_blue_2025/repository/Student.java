package noshow.Noshow_blue_2025.repository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor
public class Student {
    @Id
    @Column(name="studentId",nullable = false)
    private String studentId;
    private String name;
    private String email;
    private String password;

    @Builder
    private Student(String name, String email, String password, String studentId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.studentId = studentId;
    }
}
