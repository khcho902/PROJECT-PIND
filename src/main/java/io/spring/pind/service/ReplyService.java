package io.spring.pind.service;


import io.spring.pind.dto.ReplyDTO;

import java.util.List;

public interface ReplyService {

    List<ReplyDTO> getRepliesByProjectId(Long projectId);

    void create(ReplyDTO replyDTO);

    void modifyContent(ReplyDTO replyDTO);

    void delete(Long replyId);
}
