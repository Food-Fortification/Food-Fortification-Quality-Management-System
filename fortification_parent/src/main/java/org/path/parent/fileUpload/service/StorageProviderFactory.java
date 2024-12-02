package org.path.parent.fileUpload.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class StorageProviderFactory{

    @Value("${STORAGE_TYPE}")
    private String storageType;

    @Lazy
    @Autowired
    private S3StorageProvider s3StorageProvider;

    @Lazy
    @Autowired
    private LocalStorageProvider localStorageProvider;

    public StorageProvider getStorageProvider(){
        if(storageType.equals("S3")) return s3StorageProvider;
        return localStorageProvider;
    }
}
