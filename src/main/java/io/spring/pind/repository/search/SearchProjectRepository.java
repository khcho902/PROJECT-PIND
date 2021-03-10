package io.spring.pind.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchProjectRepository {

    Page<Object> searchProjectListWithPagination(String type, String keyword, Pageable pageable);

}
