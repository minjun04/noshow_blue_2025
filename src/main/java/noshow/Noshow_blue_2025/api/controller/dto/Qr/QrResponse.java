package noshow.Noshow_blue_2025.api.controller.dto.Qr;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import noshow.Noshow_blue_2025.infra.entity.Student;

@Getter
@Builder
@AllArgsConstructor
public class QrResponse {
    private String studentId;
    private String name;
    private String email;

    public static QrResponse from(Student student) {
        return QrResponse.builder()
                .name(student.getName())
                .studentId(student.getStudentId())
                .email(student.getEmail())
                .build();

    }
}
