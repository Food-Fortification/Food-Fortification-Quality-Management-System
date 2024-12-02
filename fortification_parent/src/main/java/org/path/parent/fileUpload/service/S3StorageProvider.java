package org.path.parent.fileUpload.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.path.parent.exceptions.CustomException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Lazy
@Component
public class S3StorageProvider implements StorageProvider {
    @Value("${S3_STORAGE_Fortification_FILES}")
    private String fortificationFiles;
    @Value("${S3_STORAGE_BUCKET_NAME}")
    private String bucket;
    @Value("${S3_STORAGE_LAB_FILES}")
    private String labFiles;
    @Value("${S3_STORAGE_MANUFACTURER_FILES}")
    private String manufacturerFiles;
    @Value("${S3_STORAGE_FORTIFICATION_BATCH_QR_CODE}")
    private String fortificationBatchQrCodes;
    @Value("${S3_STORAGE_FORTIFICATION_LOT_QR_CODE}")
    private String fortificationLotQrCodes;
    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;
    @Value("${cloud.aws.region.static}")
    private String region;
    @Value("${STORAGE_TYPE}")
    private String storageType;
    private Map<String,String> typeMap;

    private AmazonS3 amazonS3Client;

    public S3StorageProvider() {
        super();
    }

    @PostConstruct
    public void onInit(){
        if(storageType.equals("S3")){
            AWSCredentials credentials = new BasicAWSCredentials(accessKey,secretKey);
            amazonS3Client = AmazonS3ClientBuilder
                    .standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withRegion(Regions.fromName(region))
                    .build();
            typeMap = new HashMap<>();
            typeMap.put("FORTIFICATION_FILES",fortificationFiles);
            typeMap.put("LAB_FILES",labFiles);
            typeMap.put("MANUFACTURER_FILES",manufacturerFiles);
            typeMap.put("FORTIFICATION_BATCH_QR_CODE",fortificationBatchQrCodes);
            typeMap.put("FORTIFICATION_LOT_QR_CODE",fortificationLotQrCodes);
        }
    }


    @Override
    public String save(MultipartFile file, String type, Long id) {
        String key =String.format("%s_%s",new Date().getTime(), file.getOriginalFilename());
        if (id!=null){
            key = String.format("%s_%s",id,key);
        }
        String folder = typeMap.get(type);
        String filepath = folder+"/"+key;
        var metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        try {
            amazonS3Client.putObject(bucket, filepath, file.getInputStream(), metadata);
        } catch (IOException e) {
            throw new CustomException("An Exception Occurred while uploading file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return key;
    }

    @Override
    public Resource getFile(String key,String type) {
        String folder = typeMap.get(type);
        String filepath = folder+"/"+key;
        return new UrlResource(amazonS3Client.generatePresignedUrl(bucket, filepath, new Date(new Date().getTime()+36000)));
    }

    @Override
    public String save(String name, String type){
        if (!(type.equals("FORTIFICATION_BATCH_QR_CODE") || type.equals("FORTIFICATION_LOT_QR_CODE"))) return null;
        String folder = typeMap.get(type);
        String[] array = name.split("/");
        String key = String.format("%s_%s.png",array[array.length-3],array[array.length-1]);
        String filePath = folder+"/"+key;
        try{
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(name, BarcodeFormat.QR_CODE, 450, 450);
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix,"PNG",pngOutputStream);
            ByteArrayInputStream bis = new ByteArrayInputStream(pngOutputStream.toByteArray());
            var metadata = new ObjectMetadata();
            metadata.setContentType("image/png");
            amazonS3Client.putObject(bucket,filePath,bis,metadata);
        } catch (IOException | WriterException e){
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return key;
    }


}
