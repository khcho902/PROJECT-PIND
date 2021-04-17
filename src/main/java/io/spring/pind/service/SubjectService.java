package io.spring.pind.service;

import io.spring.pind.dto.SubjectDTO;

import java.util.List;

public interface SubjectService{

    List<SubjectDTO> getSubjectList(SubjectDTO subjectDTO);

    SubjectDTO getSubject(Long subjectId);
}
