package noshow.Noshow_blue_2025.api.controller.dto.Gps;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GpsRequest {
    private Double latitude;
    private Double longitude;
}
