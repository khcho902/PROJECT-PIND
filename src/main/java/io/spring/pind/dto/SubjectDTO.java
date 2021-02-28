package io.spring.pind.dto;

import io.spring.pind.entity.Region;
import io.spring.pind.entity.Subject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDTO {
    private Long id;
    private String subject1;
    private String subject2;

    public static SubjectDTO entityToDto(Subject subject){
        SubjectDTO subjectDTO = SubjectDTO.builder()
                .id(subject.getId())
                .subject1(subject.getSubjectDepth1())
                .subject2(subject.getSubjectDepth2())
                .build();
        return subjectDTO;
    }
}
