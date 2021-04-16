package io.spring.pind.dto;

import io.spring.pind.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {

    private Long id;
    private String title;
    private String description;
    private ProjectStatus status;
    private SubjectDTO subject;
    private RegionDTO region;
    private Long participateNum;
    private MemberDTO leader;
    private LocalDateTime startDate;
    private Long maxParticipateNum;
    private FileDTO file;

    public static ProjectDTO entityToDto(Project project, Subject subject, Region region, File file, Member leader, Long participateNum){

        RegionDTO regionDTO = (region == null) ? null : RegionDTO.entityToDto(region);
        SubjectDTO subjectDTO = SubjectDTO.entityToDto(subject);
        FileDTO fileDTO = (file == null) ? null : FileDTO.entityToDto(file);
        MemberDTO leaderDTO = new MemberDTO();
        leaderDTO.setId(leader.getId());
        leaderDTO.setName(leader.getName());

        ProjectDTO projectDTO = ProjectDTO.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .status(project.getStatus())
                .region(regionDTO)
                .subject(subjectDTO)
                .file(fileDTO)
                .participateNum(participateNum)
                .leader(leaderDTO)
                .startDate(project.getStartDate())
                .maxParticipateNum(project.getMaxParticiateNum())
                .build();
        return projectDTO;
    }

    public static ProjectDTO selectProjectResultToDTO(Object res){
        Object[] arr = (Object[])res;
        return ProjectDTO.entityToDto((Project)arr[0], (Subject)arr[1], (Region)arr[2], (File)arr[3], (Member)arr[4], (Long)arr[5]);
    }

    @Override
    public String toString() {
        return "ProjectDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", subject=" + subject +
                ", region=" + region +
                ", participateNum=" + participateNum +
                ", leader=" + leader +
                ", startDate=" + startDate +
                ", maxParticipateNum=" + maxParticipateNum +
                ", file=" + file +
                '}';
    }
}
