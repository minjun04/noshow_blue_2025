package noshow.Noshow_blue_2025.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BreakTimerScheduler {

    private final BreakTimerService breakTimerService;

    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void updateBreakTimers() {
        breakTimerService.checkBreakTimeAndAutoExit();
    }
}
