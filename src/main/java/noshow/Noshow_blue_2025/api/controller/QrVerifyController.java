package noshow.Noshow_blue_2025.api.controller;

import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.domain.service.QrService;
import noshow.Noshow_blue_2025.api.controller.dto.Qr.QrVerifyResponse;
import noshow.Noshow_blue_2025.api.controller.dto.Qr.QrVerifyRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class QrVerifyController {

    private final QrService qrService;

    @PostMapping("/qr/verify")
    public ResponseEntity<QrVerifyResponse> QrVerify(@RequestBody QrVerifyRequest request) {
        return qrService.verifyStudent(request);
    };
    /*
    @PostMapping("/qr/verify/dataOut")
    public QrVerifyResponse QrDataOut(@RequestBody QrVerifyResponse request) {
        return QrService.QrStudentDataOut(response);
    };
     */

}
