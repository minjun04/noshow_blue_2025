package noshow.Noshow_blue_2025.api.controller.dto;

import noshow.Noshow_blue_2025.infra.entity.Student;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthLoginResponse {
    private String studentId;
    private String name;

    public static AuthLoginResponse from(Student student) {
        return AuthLoginResponse.builder()
                .studentId(student.getStudentId())
                .name(student.getName())
                .build();
    }
}
