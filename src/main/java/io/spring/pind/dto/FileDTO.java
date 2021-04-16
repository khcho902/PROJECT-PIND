package io.spring.pind.dto;

import io.spring.pind.entity.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@Builder
@AllArgsConstructor
public class FileDTO {
    private MultipartFile uploadFile;

    private String uuid;

    private String path;

    private String fileName;

    public FileDTO() {
        uuid = "";
        path = "";
        fileName = "";
        uploadFile = null;
    }

    public static FileDTO entityToDto(File file) {
        FileDTO fileDTO = FileDTO.builder()
                .fileName(file.getFileName())
                .path(file.getPath())
                .uuid(file.getUuid())
                .build();
        return fileDTO;
    }

    public String getURL(){
        try {
            return URLEncoder.encode(path+"/"+uuid+"_"+fileName,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getThumbnailURL(){
        try {
            return URLEncoder.encode(path+"/s_"+uuid+"_"+fileName,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
