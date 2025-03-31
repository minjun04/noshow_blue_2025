package noshow.Noshow_blue_2025.api.controller.dto.Qr;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import noshow.Noshow_blue_2025.infra.entity.Student;

@Getter
@Builder
@AllArgsConstructor
public class QrVerifyResponse {
    private String studentId;
    private String name;

    public static QrVerifyResponse from(Student student) {
        return QrVerifyResponse.builder()
                .name(student.getName())
                .studentId(student.getStudentId())
                .build();

    }
}
