package noshow.Noshow_blue_2025.api.controller;

import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.domain.service.QrService;
import noshow.Noshow_blue_2025.api.controller.dto.QrVerifyResponse;
import noshow.Noshow_blue_2025.api.controller.dto.QrVerifyRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class QrVerifyController {

    private final QrService qrservice;

    public QrVerifyRequest QrVerify(@RequestBody QrVerifyRequest request) {
        return QrService.QrStudentCheck(request);
    };
    public QrVerifyResponse QrVerify(@RequestBody QrVerifyResponse request) {
        return QrService.QrStudentIdout(response);
    };

}
