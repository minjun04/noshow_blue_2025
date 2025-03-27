package noshow.Noshow_blue_2025.api.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class QrVerifyResponse {
    private String message;

    public static QrVerifyResponse from() {
        return QrVerifyResponse.builder()
                .Ema\(student.getEmail())
                .name(student.getName())
                .build();

    }
}
