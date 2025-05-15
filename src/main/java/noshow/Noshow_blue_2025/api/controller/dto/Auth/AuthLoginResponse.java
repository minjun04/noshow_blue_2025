package noshow.Noshow_blue_2025.api.controller.dto.Auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthLoginResponse {
    private String accessToken;
    private final String tokenType = "Bearer";  // 고정값

    public AuthLoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}