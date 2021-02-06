package io.spring.pind.controller;

import io.spring.pind.ProjectDTO.ProjectDTO;
import io.spring.pind.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/project")
@Log4j2
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/all")
    public ResponseEntity<List<ProjectDTO>> getAllProject(){
        List<ProjectDTO> projectDTOList = projectService.getList();
        return new ResponseEntity<>(projectDTOList, HttpStatus.OK);
    }

    @GetMapping("/{project_id}")
    public ResponseEntity<ProjectDTO> getProject(@PathVariable("project_id") Long projectId){
        ProjectDTO projectDTO = projectService.get(projectId);
        return new ResponseEntity<>(projectDTO, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Map<String, String>> registerProject(@RequestBody ProjectDTO projectDTO){
        String projectTitle = projectService.register(projectDTO);
        Map<String, String> result = new HashMap<>();
        result.put("projectTitle", projectTitle);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Map<String, String>> modifyProject(@RequestBody ProjectDTO projectDTO){
        String projectTitle = projectService.modify(projectDTO);
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