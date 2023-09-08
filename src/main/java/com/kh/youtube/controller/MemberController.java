package com.kh.youtube.controller;

import com.kh.youtube.domain.Channel;
import com.kh.youtube.domain.Member;
import com.kh.youtube.service.ChannelService;
import com.kh.youtube.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/*")
public class MemberController {

    @Autowired
    private MemberService service;

    @Autowired
    private ChannelService channelService;

    @GetMapping("/user")
    public ResponseEntity<List<Member>> showAll() {
        log.info("쭈연이 언제와");
        return ResponseEntity.status(HttpStatus.OK).body(service.showAll());
    }

    //SELECT * FROM channel WHERE id=?
    //http://localhost:8000/user/channel?id=user1
    @GetMapping("/user/channel")
    public ResponseEntity<List<Channel>> showMember(@RequestParam String id){
        return ResponseEntity.status(HttpStatus.OK).body(channelService.showMember(id));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Member> show(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(service.show(id));
    }

    @PostMapping("/user")
    public ResponseEntity<Member> create(@RequestBody Member member){
        return ResponseEntity.status(HttpStatus.OK).body(service.create(member));
    }

    @PutMapping("/user")
    public ResponseEntity<Member> update(@RequestBody Member member){
        Member result = service.update(member);
        if(result != null){
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Member> delete(@PathVariable String id){
    return ResponseEntity.status(HttpStatus.OK).body(service.delete(id));
    }



}




