package org.path.parent.qrCodeGenerate.service;
import org.path.parent.fileUpload.service.StorageClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QRCodeGenerator {

    @Lazy
    @Autowired
    private StorageClient storageClient;

    public Resource getQRCode(String key, String type){
        return storageClient.getFile(key, type);
    }

}