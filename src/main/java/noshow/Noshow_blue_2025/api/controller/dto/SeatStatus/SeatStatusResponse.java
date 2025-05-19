package noshow.Noshow_blue_2025.api.controller.dto.SeatStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SeatStatusResponse {
    private String studentId;
    private String seatId;
    private long remainingMinutes; // 남은 시간
    private int numOfExtensions;

}