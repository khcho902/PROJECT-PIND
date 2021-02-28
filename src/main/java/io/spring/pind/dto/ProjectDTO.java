package io.spring.pind.dto;

import io.spring.pind.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public static ProjectDTO entityToDto(Project project, Subject subject, Region region, Member leader, Long participateNum){

        RegionDTO regionDTO = (region == null) ? null : RegionDTO.entityToDto(region);
        SubjectDTO subjectDTO = SubjectDTO.entityToDto(subject);
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
                .participateNum(participateNum)
                .leader(leaderDTO)
                .build();
        return projectDTO;
    }

    public static ProjectDTO selectProjectResultToDTO(Object res){
        Object[] arr = (Object[])res;
        return ProjectDTO.entityToDto((Project)arr[0], (Subject)arr[1], (Region)arr[2], (Member)arr[3], (Long)arr[4]);
    }

}
