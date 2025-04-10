package noshow.Noshow_blue_2025.api.controller.dto.Auth;

import lombok.Builder;
import lombok.Data;
import noshow.Noshow_blue_2025.infra.entity.Student;

@Data
@Builder
public class AuthLoginResponse {
    private String studentId;
    private String name;
    private String email;

    public static AuthLoginResponse from(Student student) {
        return AuthLoginResponse.builder()
                .studentId(student.getStudentId())
                .name(student.getName())
                .email(student.getEmail())
                .build();
    }
}