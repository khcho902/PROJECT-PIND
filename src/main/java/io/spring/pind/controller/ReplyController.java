package io.spring.pind.controller;

import io.spring.pind.dto.ReplyDTO;
import io.spring.pind.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reply")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("{project_id}")
    public ResponseEntity<List<ReplyDTO>> getReplies(@PathVariable("project_id") Long projectId){
        List<ReplyDTO> replyList = replyService.getRepliesByProjectId(projectId);
        return new ResponseEntity<>(replyList, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity createReply(@RequestBody ReplyDTO replyDTO){
        replyService.create(replyDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity modifyReply(@RequestBody ReplyDTO replyDTO){
        replyService.modifyContent(replyDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{reply_id}")
    public ResponseEntity deleteReply(@PathVariable("reply_id") Long replyId){
        replyService.delete(replyId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
