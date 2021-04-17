package io.spring.pind.repository;

import io.spring.pind.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class AllRepositoryTest {

    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ParticipateRepository participateRepository;
    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void region_초기값입력_조회(){
        ClassPathResource resource = new ClassPathResource("region_data/bcode.csv");
        try {
            Path path = Paths.get(resource.getURI());
            List<String> content = Files.readAllLines(path);
            content.forEach(input -> {

                String[] arr = new String[4];
                for(int i = 0; i < 4; i++) {
                    arr[i] = null;
                }

                String[] inputs = input.split(",");
                for(int i = 0; i < inputs.length; i++) {
                    arr[i] = inputs[i];
                }

                Region region = Region.builder()
                        .id(Long.valueOf(arr[0]))
                        .regionDepth1(arr[1])
                        .regionDepth2(arr[2])
                        .regionDepth3(arr[3])
                        .build();
                regionRepository.save(region);
            });
        } catch (IOException e) {
            System.out.println(e);
        }

        List<Region> list = regionRepository.findAll();
        list.stream().forEach(region -> {
            System.out.println(region.toString());
        });
    }

    @Test
    public void subject_초기값입력_조회(){
        Subject subject0 = Subject.builder().subjectDepth1("프로그래밍").build();
        Subject subject1 = Subject.builder().subjectDepth1("프로그래밍").subjectDepth2("자바").build();
        Subject subject2 = Subject.builder().subjectDepth1("프로그래밍").subjectDepth2("파이썬").build();
        Subject subject3 = Subject.builder().subjectDepth1("프로그래밍").subjectDepth2("루비").build();
        Subject subject4 = Subject.builder().subjectDepth1("프로그래밍").subjectDepth2("자바스크립").build();
        subjectRepository.save(subject0);
        subjectRepository.save(subject1);
        subjectRepository.save(subject2);
        subjectRepository.save(subject3);
        subjectRepository.save(subject4);

        List<Subject> list = subjectRepository.findAll();
        list.stream().forEach(subject -> {
            System.out.println(subject.toString());
        });
    }

    @Test
    public void member_초기값입력_조회(){
        IntStream.rangeClosed(1, 50).forEach(i -> {
            Member member = Member.builder()
                    .email("member" + i + "@gmail.com")
                    .password("1111")
                    .name("member" + i)
                    .build();
            memberRepository.save(member);
        });

        List<Member> list = memberRepository.findAll();
        list.stream().forEach(member -> {
            System.out.println(member.toString());
        });
    }

    @Test
    public void project_초기값입력_조회(){
        Subject subject = subjectRepository.findById(1L).get();
        Region region = regionRepository.findById(1168010300L).get();

        IntStream.rangeClosed(1, 50).forEach(i -> {
            Project project = Project.builder()
                    .subject(subject)
                    .region(region)
                    .title(i + "번째 project!!")
                    .description(i + "번째 project 입니다.~~~")
                    .content("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
                    .status(ProjectStatus.values()[i % 3])
                    .maxParticiateNum(100L)
                    .startDate(LocalDateTime.now().plusMonths(1))
                    .build();
            projectRepository.save(project);
        });

        List<Project> list = projectRepository.findAll();
        list.stream().forEach(project -> {
            System.out.println(project.toString());
        });
    }

    @Test
    public void participate_초기값입력(){
        for(long projectId = 1; projectId <= 50; projectId++){
            Project project = projectRepository.findById(projectId).get();
            for(long memberId = 1; memberId <= 50; memberId++){
                Member member = memberRepository.findById(memberId).get();
                ParticipateRole role = projectId == memberId ? ParticipateRole.LEADER : ParticipateRole.MEMBER;

                Participate participate = Participate.builder()
                        .project(project)
                        .member(member)
                        .role(role)
                        .build();
                participateRepository.save(participate);
            }
        }
    }

    @Test
    public void reply_초기값입력(){

        for(long projectId = 1; projectId <= 5; projectId++){
            Project project = projectRepository.findById(projectId).get();
            for(long memberId = 1; memberId <= 5; memberId++){
                Member member = memberRepository.findById(memberId).get();
                Reply reply = Reply.builder()
                        .member(member)
                        .project(project)
                        .content("reply written by "+ member.getName())
                        .build();
                replyRepository.save(reply);
            }
        }
    }
}
