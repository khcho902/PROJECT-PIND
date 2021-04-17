package io.spring.pind.service;

import io.spring.pind.dto.RegionDTO;
import io.spring.pind.dto.SubjectDTO;
import io.spring.pind.entity.Region;
import io.spring.pind.entity.Subject;
import io.spring.pind.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService{

    private final SubjectRepository subjectRepository;

    @Override
    public List<SubjectDTO> getSubjectList(SubjectDTO subjectDTO) {
        List<Subject> result;
        if (subjectDTO.getSubject1() == null){
            result = subjectRepository.getDepthOneSubjectList();
        }else {
            result = subjectRepository.getDepthTwoSubjectList(subjectDTO.getSubject1());
        }

        List<SubjectDTO> subjectDTOList = new ArrayList<>();
        result.forEach(res -> {
            subjectDTOList.add(SubjectDTO.entityToDto(res));
        });
        return subjectDTOList;
    }

    @Override
    public SubjectDTO getSubject(Long subjectId) {
        Subject result = subjectRepository.findById(subjectId).get();
        return SubjectDTO.entityToDto(result);
    }
}
