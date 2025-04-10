package noshow.Noshow_blue_2025.api.controller.dto.Auth;

import lombok.Data;

@Data
public class AuthSignUpRequest {
    private String email;
    private String name;
    private String password;
    private String studentId;
}