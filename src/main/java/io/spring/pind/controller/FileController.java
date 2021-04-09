package io.spring.pind.controller;

import io.spring.pind.dto.FileDTO;
import io.spring.pind.dto.UploadResultDTO;
import io.spring.pind.service.FileService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Log4j2
@RequestMapping("/file")
public class FileController {
    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private FileService fileService;

    @GetMapping("")
    public ResponseEntity<byte[]> getFile(String fileName, String size) {
        ResponseEntity<byte[]> result = null;
        try {
            String srcFileName =  URLDecoder.decode(fileName,"UTF-8");
            File file = new File(uploadPath + File.separator + srcFileName);
            if(size != null && size.equals("1")){
                file  = new File(file.getParent(), file.getName().substring(2));
            }
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @PostMapping("")
    public ResponseEntity<List<UploadResultDTO>> createFile(MultipartFile[] uploadFiles){
        List<UploadResultDTO> resultDTOList = new ArrayList<>();
        for (MultipartFile uploadFile: uploadFiles) {
            UploadResultDTO resultDTO = fileService.createFile(uploadFile);
            if (resultDTO != null) {
                resultDTOList.add(resultDTO);
            }
        }
        return new ResponseEntity<>(resultDTOList, HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<Boolean> deleteFile(String fileName) {
        try {
            boolean result = fileService.deleteFile(fileName);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/path")
    public ResponseEntity<FileDTO> getFilePath(String fileName){
        FileDTO fileDTO = FileDTO.builder()
                .fileName(fileName)
                .path(fileService.makeFolder())
                .uuid(UUID.randomUUID().toString())
                .build();
        return new ResponseEntity<>(fileDTO, HttpStatus.OK);
    }
}
