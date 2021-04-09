package io.spring.pind.repository;

import io.spring.pind.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query("SELECT r, m FROM Reply r " +
            "LEFT JOIN r.member m " +
            "WHERE r.project.id =:projectId")
    List<Object> getRepliesByProjectId(Long projectId);

    void deleteByProjectId(Long projectId);
}
