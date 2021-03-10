package io.spring.pind.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"participateList", "subject", "region"})
public class Project extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "max_participate_num", nullable = false)
    private Long maxParticiateNum;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    final private List<Participate> participateList = new ArrayList<>();

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeDescription(String description){
        this.description = description;
    }

    public void changeStatus(ProjectStatus status){
        this.status = status;
    }

    public void changeRegion(Region region) {
        this.region = region;
    }

    public void changeSubject(Subject subject){
        this.subject = subject;
    }

    public void changeStartDate(LocalDateTime startDate){
        this.startDate = startDate;
    }

    public void changeMaxParticipateNum(Long maxParticiateNum){
        this.maxParticiateNum = maxParticiateNum;
    }
}
