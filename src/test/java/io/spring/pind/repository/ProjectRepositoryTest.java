package io.spring.pind.repository;

import io.spring.pind.entity.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    public void insertProjects(){
        IntStream.rangeClosed(1, 100).forEach(i -> {
                Project  project = Project.builder()
                        .title(i + "번째 project!!")
                        .description(i + "번째 project 입니다.~~~")
                        .status(i % 3).build();
                projectRepository.save(project);
        });
    }

    @Test
    public void selectAllProject(){
        List<Project> list = projectRepository.findAll();
        list.stream().forEach(project -> {
            System.out.println(project.toString());
        });
    }
}
