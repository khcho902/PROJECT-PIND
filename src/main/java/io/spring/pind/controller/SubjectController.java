package io.spring.pind.controller;

import io.spring.pind.dto.RegionDTO;
import io.spring.pind.dto.SubjectDTO;
import io.spring.pind.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subject")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping
    public ResponseEntity<List<SubjectDTO>> getSubjectList(SubjectDTO subjectDTO){
        List<SubjectDTO> subjectList = subjectService.getSubjectList(subjectDTO);
        return new ResponseEntity<>(subjectList, HttpStatus.OK);
    }

    @GetMapping("/{subject_id}")
    public ResponseEntity<SubjectDTO> getSubject(@PathVariable("subject_id") Long subjectId){
        SubjectDTO subjectDTO = subjectService.getSubject(subjectId);
        return new ResponseEntity<>(subjectDTO, HttpStatus.OK);
    }
}
