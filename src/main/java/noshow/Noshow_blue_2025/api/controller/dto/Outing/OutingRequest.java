package noshow.Noshow_blue_2025.api.controller.dto.Outing;
import lombok.Data;

@Data
public class OutingRequest {
    private boolean isBreak; // true면 휴식, false면 반납
}