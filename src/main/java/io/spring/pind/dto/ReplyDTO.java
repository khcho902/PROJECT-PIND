package io.spring.pind.dto;


import io.spring.pind.entity.Member;
import io.spring.pind.entity.Reply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {

    private Long id;
    private Long project_id;
    private Long member_id;
    private String member_name;
    private String content;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    public static ReplyDTO entityToDto(Reply reply, Member member){

        ReplyDTO replyDTO = ReplyDTO.builder()
                .id(reply.getId())
                .project_id(reply.getProject().getId())
                .member_id(member.getId())
                .member_name(member.getName())
                .content(reply.getContent())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();
        return replyDTO;
    }

    public static ReplyDTO selectReplyResultToDTO(Object res){
        Object[] arr = (Object[])res;
        return ReplyDTO.entityToDto((Reply)arr[0], (Member)arr[1]);
    }
}
