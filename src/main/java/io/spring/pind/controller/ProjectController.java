package io.spring.pind.controller;

import io.spring.pind.dto.*;
import io.spring.pind.service.FileService;
import io.spring.pind.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/project")
@Log4j2
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final FileService fileService;

    @GetMapping("/list")
    public ResponseEntity<PageResultDTO<Object, ProjectDTO>> searchProjectListWithPagination(PageRequestDTO pageRequestDTO){
        PageResultDTO<Object, ProjectDTO> projectListWithPagination = projectService.searchProjectListWithPagination(pageRequestDTO);
        return new ResponseEntity<>(projectListWithPagination, HttpStatus.OK);
    }

    @GetMapping("/{project_id}")
    public ResponseEntity<ProjectDTO> getProject(@PathVariable("project_id") Long projectId){
        ProjectDTO projectDTO = projectService.getDetail(projectId);
        return new ResponseEntity<>(projectDTO, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Map<String, String>> createProject(@RequestPart(value = "project") ProjectDTO projectDTO, @RequestPart(value = "file") MultipartFile file){

        System.out.println("-------------------");
        System.out.println(projectDTO);
        System.out.println("-------------------");

        if (file != null){
            UploadResultDTO uploadResultDTO = fileService.createFile(file);
            projectDTO.setFile(new FileDTO());
            projectDTO.getFile().setFileName(uploadResultDTO.getFileName());
            projectDTO.getFile().setPath(uploadResultDTO.getFolderPath());
            projectDTO.getFile().setUuid(uploadResultDTO.getUuid());
        }
        String projectTitle = projectService.create(projectDTO);

        Map<String, String> result = new HashMap<>();
        result.put("projectTitle", projectTitle);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Map<String, String>> modifyProject(@RequestBody ProjectDTO projectDTO){
        String projectTitle = projectService.modifyContent(projectDTO);
        Map<String, String> result = new HashMap<>();
        result.put("projectTitle", projectTitle);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{project_id}")
    public ResponseEntity<Map<String, String>> deleteProject(@PathVariable("project_id") Long projectId){
        String projectTitle = projectService.delete(projectId);
        Map<String, String> result =  new HashMap<>();
        result.put("projectTitle" , projectTitle);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
