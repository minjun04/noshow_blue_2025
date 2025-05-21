package noshow.Noshow_blue_2025.api.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SeatRemainingNumOfExtensionResponse {
    private String seatId;
    private int numOfExtensions;
    private long remainingMinutes;
}
