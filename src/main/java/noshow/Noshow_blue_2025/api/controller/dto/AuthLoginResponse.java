package noshow.Noshow_blue_2025.api.controller.dto;

import noshow.Noshow_blue_2025.infra.entity.Student;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthLoginResponse {
    private String Email;
    private String Name;

    public static AuthLoginResponse from(Student student) {
        return AuthLoginResponse.builder()
                .Email(student.getEmail())
                .name(student.getName())
                .build();
    }
}
