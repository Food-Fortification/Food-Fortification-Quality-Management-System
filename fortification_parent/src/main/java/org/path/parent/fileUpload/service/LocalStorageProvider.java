package org.path.parent.fileUpload.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.path.parent.exceptions.CustomException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class LocalStorageProvider implements StorageProvider{

    @Value("${UPLOAD_PATH}")
    private String uploadFolder;

    private Path root;

    private Map<String,String> typeMap;

    @PostConstruct
    public void onInit(){
        typeMap = new HashMap<>();
        typeMap.put("FORTIFICATION_FILES","fortificationFiles");
        typeMap.put("LAB_FILES","labFiles");
        typeMap.put("MANUFACTURER_FILES","manufacturerFiles");
        typeMap.put("FORTIFICATION_BATCH_QR_CODE","fortificationBatchQrCodes");
        typeMap.put("FORTIFICATION_LOT_QR_CODE","fortificationLotQrCodes");
    }

    @Override
    public Resource getFile(String key, String type) {
        String folder = uploadFolder + "/" + typeMap.get(type);
        root = Paths.get(folder);
        try {
            Path file = root.resolve(key);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public String save(MultipartFile file, String type, Long id) {
        String key =String.format("%s_%s",new Date().getTime(), file.getOriginalFilename());
        if (id!=null){
            key = String.format("%s_%s",id,key);
        }
        String folder = uploadFolder + "/" + typeMap.get(type);
        root = Paths.get(folder);

        try {
            Files.copy(file.getInputStream(), root.resolve(Objects.requireNonNull(key)));
            return key;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String save(String name, String type){
        if (!(type.equals("FORTIFICATION_BATCH_QR_CODE") || type.equals("FORTIFICATION_LOT_QR_CODE"))) return null;
        String folder = uploadFolder + "/" + typeMap.get(type);
        root = Paths.get(folder);
        String[] array = name.split("/");
        String key = String.format("%s_%s.png",array[array.length-3],array[array.length-1]);
        try{
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(name, BarcodeFormat.QR_CODE, 450, 450);
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix,"PNG",pngOutputStream);
            ByteArrayInputStream bis = new ByteArrayInputStream(pngOutputStream.toByteArray());
            Files.copy(bis, root.resolve(Objects.requireNonNull(key)));
        } catch (IOException | WriterException e){
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return key;
    }
}