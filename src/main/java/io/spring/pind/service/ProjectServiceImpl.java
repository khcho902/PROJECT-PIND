package io.spring.pind.service;

import io.spring.pind.dto.ProjectDTO;
import io.spring.pind.entity.Project;
import io.spring.pind.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;

    @Override
    public List<ProjectDTO> getList() {
        List<ProjectDTO> list = new ArrayList<>();
        projectRepository.findAll().forEach(project ->{
            list.add(entityToDto(project));
        });
        return list;
    }

    @Override
    public ProjectDTO get(Long id) {
        Optional<Project> result = projectRepository.findById(id);
        return result.isPresent() ? entityToDto(result.get()) : null;
    }

    @Override
    public String register(ProjectDTO projectDTO) {
        projectRepository.save(dtoToEntity(projectDTO));
        return projectDTO.getTitle();
    }

    @Override
    public String modify(ProjectDTO projectDTO) {
        Optional<Project> result = projectRepository.findById(projectDTO.getId());
        if (result.isPresent()){
            Project project = result.get();
            project.changeTitle(projectDTO.getTitle());
            project.changeDescription(projectDTO.getDescription());
            project.changeStatus(projectDTO.getStatus());
            projectRepository.save(project);
            return result.get().getTitle();
        }
        return null;
    }

    @Override
    public String delete(Long projectId) {
        Optional<Project> result = projectRepository.findById(projectId);
        if (result.isPresent()){
            projectRepository.deleteById(projectId);
            return result.get().getTitle();
        }
        return null;
    }
}
