package noshow.Noshow_blue_2025.controller.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class AuthSignUpRequest {
    private String email;
    private String name;
    private String password;
    private String studentId;
}