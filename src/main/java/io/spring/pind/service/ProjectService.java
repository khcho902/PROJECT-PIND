package io.spring.pind.service;

import io.spring.pind.dto.ProjectDTO;
import io.spring.pind.entity.Project;
import java.util.List;

public interface ProjectService {

    List<ProjectDTO> getList();

    ProjectDTO get(Long id);

    String register(ProjectDTO projectDTO);

    String modify(ProjectDTO projectDTO);

    String delete(Long projectId);

    default ProjectDTO entityToDto(Project project){
        ProjectDTO projectDTO = ProjectDTO.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .status(project.getStatus())
                .build();
        return projectDTO;
    }

    default Project dtoToEntity(ProjectDTO projectDTO){
        Project project = Project.builder()
                .id(projectDTO.getId())
                .title(projectDTO.getTitle())
                .description(projectDTO.getDescription())
                .status(projectDTO.getStatus())
                .build();
        return project;
    }
}
