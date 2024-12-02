package org.path.parent.fileUpload.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageProvider {
    String save(MultipartFile file, String type, Long id);
    Resource getFile(String key,String type);
    String save(String name, String type);

}
