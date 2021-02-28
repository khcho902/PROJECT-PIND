package io.spring.pind.repository;

import io.spring.pind.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT p, s, r, m, count(pa)" +
            " FROM Project p " +
            " LEFT JOIN p.subject s" +
            " LEFT OUTER JOIN p.region r" +
            " LEFT JOIN Participate pa ON pa.project = p" +
            " LEFT JOIN Participate pa_tmp on pa_tmp.project = p" +
            " LEFT JOIN pa_tmp.member m" +
            " WHERE pa_tmp.role = 'LEADER'" +
            " GROUP BY p")
    List<Object> getProjectAll();

    @Query("SELECT p, s, r, m, count(pa)" +
            " FROM Project p " +
            " LEFT JOIN p.subject s" +
            " LEFT OUTER JOIN p.region r" +
            " LEFT JOIN Participate pa ON pa.project = p" +
            " LEFT JOIN Participate pa_tmp on pa_tmp.project = p" +
            " LEFT JOIN pa_tmp.member m" +
            " WHERE p.id = :id AND pa_tmp.role = 'LEADER'" +
            " GROUP BY p")
    Object getProjectById(@Param("id") Long id);

}
