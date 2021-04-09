package io.spring.pind.repository;

import io.spring.pind.entity.Project;
import io.spring.pind.repository.search.SearchProjectRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<Project, Long>, SearchProjectRepository {

    @Query("SELECT p, s, r, f, m, count(pa)" +
            " FROM Project p " +
            " LEFT JOIN p.subject s" +
            " LEFT OUTER JOIN p.region r" +
            " LEFT OUTER JOIN p.file f" +
            " LEFT JOIN Participate pa ON pa.project = p" +
            " LEFT JOIN Participate pa_tmp on pa_tmp.project = p" +
            " LEFT JOIN pa_tmp.member m" +
            " WHERE p.id = :id AND pa_tmp.role = 'LEADER'" +
            " GROUP BY p")
    Object getProjectById(@Param("id") Long id);

}
