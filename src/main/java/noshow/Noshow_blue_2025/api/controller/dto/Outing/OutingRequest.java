package noshow.Noshow_blue_2025.api.controller.dto.Outing;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OutingRequest {
    @JsonProperty("isBreak")
    private boolean isBreak; // true면 휴식, false면 반납
}