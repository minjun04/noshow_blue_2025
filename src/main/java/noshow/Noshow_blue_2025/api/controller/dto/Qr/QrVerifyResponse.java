package noshow.Noshow_blue_2025.api.controller.dto.Qr;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import noshow.Noshow_blue_2025.infra.entity.Student;

@Getter
@Builder
public class QrVerifyResponse {
    private String StudentId;
    private String Name;
    private String Email;

    public static QrVerifyResponse from(Student student) {
        return QrVerifyResponse.builder()
                .Email(student.getEmail())
                .Name(student.getName())
                .StudentId(student.getStudentId())
                .build();

    }
}
