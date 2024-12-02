package org.path.parent.qrCodeGenerate.controller;

import org.path.parent.qrCodeGenerate.service.QRCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping(path = "QrCode")
@RequiredArgsConstructor
@CrossOrigin(origins = {"*"})
public class QRCodeController {
    private final QRCodeGenerator qrCodeGenerator;

    @GetMapping(value = "/download")
    public ResponseEntity<?> getQRCode(@RequestParam("key") String key, @RequestParam("type") String type) throws IOException {
        Resource qrArray = qrCodeGenerator.getQRCode(key, type);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .contentLength(qrArray.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename(key)
                                .build().toString())
                .body(qrArray);
    }

    @GetMapping(value = "/view")
    public ResponseEntity<?> viewQRCode(@RequestParam("key") String key, @RequestParam("type") String type) throws IOException {
        Resource qrArray = qrCodeGenerator.getQRCode(key, type);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .contentLength(qrArray.contentLength())
                .body(qrArray);
    }

}
