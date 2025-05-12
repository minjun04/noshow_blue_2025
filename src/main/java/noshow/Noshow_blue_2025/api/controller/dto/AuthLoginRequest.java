package noshow.Noshow_blue_2025.api.controller.dto;

import lombok.Data;

@Data
public class AuthLoginRequest {
    private String email;
    private String password;
}