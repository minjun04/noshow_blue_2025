package noshow.Noshow_blue_2025.api.controller.dto.Auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthLoginRequest {
    String idToken;
}
