package io.spring.pind.service;

import io.spring.pind.dto.FileDTO;
import io.spring.pind.dto.UploadResultDTO;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class FileService {
    @Value("${upload.path}")
    private String uploadPath;

    public String makeFolder() {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath =  str.replace("//", File.separator);
        File uploadPathFolder = new File(uploadPath, folderPath);

        if (uploadPathFolder.exists() == false) {
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }

    public UploadResultDTO createFile(MultipartFile uploadFile){
//        String originalName = uploadFile.getOriginalFilename();
//        String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
//        String folderPath = makeFolder();
//        String uuid = UUID.randomUUID().toString();
        String originalName = uploadFile.getOriginalFilename();
        FileDTO fileDTO = FileDTO.builder()
                .uploadFile(uploadFile)
                .fileName(originalName.substring(originalName.lastIndexOf("\\") + 1))
                .path(makeFolder())
                .uuid(UUID.randomUUID().toString())
                .build();

        String saveName = uploadPath + File.separator + fileDTO.getPath() + File.separator + fileDTO.getUuid() +"_" + fileDTO.getFileName();
        Path savePath = Paths.get(saveName);

        try {
            fileDTO.getUploadFile().transferTo(savePath);
            if(fileDTO.getUploadFile().getContentType().startsWith("image") == true) {
                String thumbnailSaveName = uploadPath + File.separator + fileDTO.getPath() + File.separator
                        + "s_" + fileDTO.getUuid() + "_" + fileDTO.getFileName();
                File thumbnailFile = new File(thumbnailSaveName);
                Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 100, 100);
            }
            return new UploadResultDTO(fileDTO.getFileName(),fileDTO.getUuid(),fileDTO.getPath() );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean deleteFile(String fileName) throws Exception{
        String srcFileName = null;
        srcFileName = URLDecoder.decode(fileName,"UTF-8");
        File file = new File(uploadPath + File.separator + srcFileName);
        String mimeType = Files.probeContentType(file.toPath());
        boolean result = file.delete();

        if (mimeType.startsWith("image") == true) {
            File thumbnail = new File(file.getParent(), "s_" + file.getName());
            result = thumbnail.delete();
        }
        return result;
    }
}
