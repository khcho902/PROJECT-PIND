package io.spring.pind.repository;

import io.spring.pind.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    @Test
    public void region_초기값입력_조회(){
        Region region1 = Region.builder().regionDepth1("서울시").regionDepth2("마포구").regionDepth3("서교동").build();
        Region region2 = Region.builder().regionDepth1("서울시").regionDepth2("마포구").regionDepth3("연희동").build();
        Region region3 = Region.builder().regionDepth1("서울시").regionDepth2("강남구").regionDepth3("역삼동").build();
        Region region4 = Region.builder().regionDepth1("서울시").regionDepth2("종로구").regionDepth3("부암동").build();
        regionRepository.save(region1);
        regionRepository.save(region2);
        regionRepository.save(region3);
        regionRepository.save(region4);

        List<Region> list = regionRepository.findAll();
        list.stream().forEach(region -> {
            System.out.println(region.toString());
        });
    }

    @Test
    public void subject_초기값입력_조회(){
        Subject subject1 = Subject.builder().subjectDepth1("프로그래밍").subjectDepth2("자바").build();
        Subject subject2 = Subject.builder().subjectDepth1("프로그래밍").subjectDepth2("파이썬").build();
        Subject subject3 = Subject.builder().subjectDepth1("프로그래밍").subjectDepth2("루비").build();
        Subject subject4 = Subject.builder().subjectDepth1("프로그래밍").subjectDepth2("자바스크립").build();
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
        IntStream.rangeClosed(1, 10).forEach(i -> {
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

        IntStream.rangeClosed(1, 10).forEach(i -> {
            Project project = Project.builder()
                    .subject(subject)
                    .title(i + "번째 project!!")
                    .description(i + "번째 project 입니다.~~~")
                    .status(ProjectStatus.values()[i % 3]).build();
            projectRepository.save(project);
        });

        List<Project> list = projectRepository.findAll();
        list.stream().forEach(project -> {
            System.out.println(project.toString());
        });
    }

    @Test
    public void participate_초기값입력_조회(){

        Project project = projectRepository.findById(1L).get();
        Member member = memberRepository.findById(1L).get();
        Participate participate = Participate.builder()
                .project(project).member(member).role(ParticipateRole.LEADER)
                .build();
        participateRepository.save(participate);

        project = projectRepository.findById(1L).get();
        member = memberRepository.findById(2L).get();
        participate = Participate.builder()
                .project(project).member(member).role(ParticipateRole.MEMBER)
                .build();
        participateRepository.save(participate);

        project = projectRepository.findById(2L).get();
        member = memberRepository.findById(3L).get();
        participate = Participate.builder()
                .project(project).member(member).role(ParticipateRole.LEADER)
                .build();
        participateRepository.save(participate);
    }
}
