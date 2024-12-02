package org.path.parent.fileUpload.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;

@Lazy
@Component
public class StorageClient {
    private StorageProvider storageProvider;
    @Autowired
    private StorageProviderFactory storageProviderFactory;

    public StorageClient(){
    }

    @PostConstruct
    public void onInit(){
        this.storageProvider = storageProviderFactory.getStorageProvider();
    }

    public String save(MultipartFile file, String type, Long id) {
        return this.storageProvider.save(file, type, id);
    }

    public Resource getFile(String key, String type) {
        return this.storageProvider.getFile(key, type);
    }
    public String save(String text, String type){return this.storageProvider.save(text, type);}
}
