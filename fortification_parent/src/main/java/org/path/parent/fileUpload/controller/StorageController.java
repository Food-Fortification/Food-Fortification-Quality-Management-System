package org.path.parent.fileUpload.controller;
import org.path.parent.fileUpload.service.StorageClient;
import org.path.parent.utils.FileValidator;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Tag(name = "Storage Controller")
@RequestMapping("storage")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class StorageController {

    @Lazy
    @Autowired
    private StorageClient storageClient;
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("type")String type,
                                        @RequestParam(required = false) Long id) {
        FileValidator.validateFileUpload(file.getOriginalFilename());
        String response;
        try {
            response = storageClient.save(file, type, id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            String message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @GetMapping("/get-file")
    public ResponseEntity<Resource> getFile(@RequestParam("key") String key,@RequestParam("type")String type) {
        Resource file = storageClient.getFile(key,type);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
    @GetMapping("/qrcode")
    public ResponseEntity<?> qrcodeGen(@RequestParam("text") String text,@RequestParam("type")String type) {
        String file = storageClient.save(text,type);
        return ResponseEntity.ok(file);
    }
}