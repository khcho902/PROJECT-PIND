package io.spring.pind.service;

import io.spring.pind.dto.PageRequestDTO;
import io.spring.pind.dto.PageResultDTO;
import io.spring.pind.dto.ProjectDTO;
import io.spring.pind.entity.*;
import io.spring.pind.repository.MemberRepository;
import io.spring.pind.repository.ProjectRepository;
import io.spring.pind.repository.RegionRepository;
import io.spring.pind.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final SubjectRepository subjectRepository;
    private final RegionRepository regionRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    @Override
    public PageResultDTO<Object, ProjectDTO> getListWithPagination(PageRequestDTO pageRequestDTO) {

        Function<Object, ProjectDTO> fn = (ProjectDTO::selectProjectResultToDTO);

        Page<Object> result = projectRepository.getProjectListWithPagination(pageRequestDTO.getPageable(Sort.by("id").descending()));
        return new PageResultDTO<>(result, fn);
    }

    @Transactional(readOnly = true)
    @Override
    public ProjectDTO getDetail(Long id) {

        Object selectResult = projectRepository.getProjectById(id);
        return ProjectDTO.selectProjectResultToDTO(selectResult);
    }

    @Transactional
    @Override
    public String create(ProjectDTO projectDTO) {

        String title = projectDTO.getTitle();
        String description = projectDTO.getDescription();
        Subject subject = subjectRepository.findById(projectDTO.getSubject().getId()).get();
        Region region = regionRepository.findById(projectDTO.getRegion().getId()).get();

        Project newProject = Project.builder()
                .title(title)
                .description(description)
                .status(ProjectStatus.RECRUIT)
                .subject(subject)
                .region(region)
                .build();

        Member leader = memberRepository.findById(projectDTO.getLeader().getId()).get();
        Participate participate = Participate.builder()
                .member(leader)
                .project(newProject)
                .role(ParticipateRole.LEADER)
                .build();
        newProject.getParticipateList().add(participate);

        projectRepository.save(newProject);
        return newProject.getTitle();
    }

    @Transactional
    @Override
    public String modifyContent(ProjectDTO projectDTO) {
        Optional<Project> result = projectRepository.findById(projectDTO.getId());

        if (result.isPresent()){
            Region region = regionRepository.findById(projectDTO.getRegion().getId()).get();
            Subject subject = subjectRepository.findById(projectDTO.getSubject().getId()).get();

            Project project = result.get();
            project.changeTitle(projectDTO.getTitle());
            project.changeDescription(projectDTO.getDescription());
            project.changeStatus(projectDTO.getStatus());
            project.changeRegion(region);
            project.changeSubject(subject);

            return project.getTitle();
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
