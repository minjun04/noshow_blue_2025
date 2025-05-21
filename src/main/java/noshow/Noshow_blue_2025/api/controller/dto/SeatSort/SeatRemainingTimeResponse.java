package noshow.Noshow_blue_2025.api.controller.dto.SeatSort;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SeatRemainingTimeResponse {
    private String seatId;
    private long remainingMinutes; // 남은 시간
}
