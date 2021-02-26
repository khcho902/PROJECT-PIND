package io.spring.pind.service;

import io.spring.pind.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {

    List<ProjectDTO> getAllList();

    ProjectDTO getDetail(Long id);

    String create(ProjectDTO projectDTO);

    String modifyContent(ProjectDTO projectDTO);

    String delete(Long projectId);

}
