package org.path.parent.utils;

import org.path.parent.exceptions.CustomException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;

import java.net.URL;
import java.util.List;
import java.util.Objects;

public class FileValidator {

    private static List<String> allowedFileExtensions = List.of("img","png","pdf","jpg","jpeg","docx","doc","xlsx","xlsb","xlsm","xltx");

    private static final List<String> specialCharacters = List.of("/", ",", ":", "*", "?", "\\", "<", ">", "|", "~", "@", "%", "#", "$", "&", "!", "=", "+");

    public static void validateFileUpload(String fileName){
        if (specialCharacters.stream().anyMatch(fileName::contains)) {
            throw new CustomException("Invalid file name", HttpStatus.BAD_REQUEST);
        }
        String extension = FilenameUtils.getExtension(fileName);
        if (allowedFileExtensions.stream().noneMatch(e -> Objects.equals(e, extension)))throw new CustomException("Oops! Invalid File Type, Please choose a valid file format (eg doc, pdf, ..)", HttpStatus.BAD_REQUEST);
        if (isURL(fileName)) throw new CustomException("Invalid file path", HttpStatus.BAD_REQUEST);
    }

    public static boolean isURL(String url) {
        try {
            new URL(url);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
