package noshow.Noshow_blue_2025.api.controller.dto.Qr;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import noshow.Noshow_blue_2025.infra.entity.Student;

@Getter
@Builder
@AllArgsConstructor
public class QrResponse {
    private String name;
    private String studentId;
    private String email;
    private Integer entry;

    public static QrResponse from(Student student) {
        return QrResponse.builder()
                .name(student.getName())
                .studentId(student.getStudentId())
                .email(student.getEmail())
                .entry(student.getEntry())
                .build();

    }
}
