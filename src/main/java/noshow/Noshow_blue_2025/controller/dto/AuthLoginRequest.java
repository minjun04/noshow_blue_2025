package noshow.Noshow_blue_2025.controller.dto;

import lombok.Data;

@Data
public class AuthLoginRequest {
    private String email;
    private String password;
}