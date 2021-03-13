package io.spring.pind.service;

import io.spring.pind.dto.PageRequestDTO;
import io.spring.pind.dto.PageResultDTO;
import io.spring.pind.dto.ProjectDTO;

public interface ProjectService {

    PageResultDTO<Object, ProjectDTO> searchProjectListWithPagination(PageRequestDTO pageRequestDTO);

    ProjectDTO getDetail(Long id);

    String create(ProjectDTO projectDTO);

    String modifyContent(ProjectDTO projectDTO);

    String delete(Long projectId);

}
