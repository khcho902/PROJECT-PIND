package io.spring.pind.repository;

import io.spring.pind.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Member, Long> {
}
