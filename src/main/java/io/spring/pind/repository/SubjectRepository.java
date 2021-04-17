package io.spring.pind.repository;

import io.spring.pind.entity.Region;
import io.spring.pind.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query("SELECT s FROM Subject s" +
            " WHERE s.subjectDepth2 IS NULL ")
    List<Subject> getDepthOneSubjectList();

    @Query("SELECT s FROM Subject s" +
            " WHERE s.subjectDepth1 = :subjectDepth1" +
            " AND s.subjectDepth2 IS NOT NULL")
    List<Subject> getDepthTwoSubjectList(@Param("subjectDepth1") String subjectDepth1);
}
