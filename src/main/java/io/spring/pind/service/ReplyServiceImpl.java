package io.spring.pind.service;

import io.spring.pind.dto.ReplyDTO;
import io.spring.pind.entity.Member;
import io.spring.pind.entity.Project;
import io.spring.pind.entity.Reply;
import io.spring.pind.repository.MemberRepository;
import io.spring.pind.repository.ProjectRepository;
import io.spring.pind.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;

    @Transactional(readOnly = true)
    @Override
    public List<ReplyDTO> getRepliesByProjectId(Long projectId) {

        List<ReplyDTO> replyList = new ArrayList<>();
        replyRepository.getRepliesByProjectId(projectId).forEach(selectResult -> {
            replyList.add(ReplyDTO.selectReplyResultToDTO(selectResult));
        });
        return replyList;
    }

    @Transactional
    @Override
    public void create(ReplyDTO replyDTO) {

        Member member = memberRepository.getOne(replyDTO.getMember_id());
        Project project = projectRepository.getOne(replyDTO.getProject_id());

        Reply newReply = Reply.builder()
                .project(project)
                .member(member)
                .content(replyDTO.getContent())
                .build();

        replyRepository.save(newReply);
    }

    @Transactional
    @Override
    public void modifyContent(ReplyDTO replyDTO) {
        Optional<Reply> result = replyRepository.findById(replyDTO.getId());

        if (result.isPresent()){
            Reply reply = result.get();
            reply.changeContent(replyDTO.getContent());
        }
    }

    @Transactional
    @Override
    public void delete(Long replyId) {
        replyRepository.deleteById(replyId);
    }
}
