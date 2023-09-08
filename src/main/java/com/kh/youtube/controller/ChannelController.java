package com.kh.youtube.controller;

import com.kh.youtube.domain.Channel;
import com.kh.youtube.service.ChannelService;
import com.kh.youtube.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/*")
public class ChannelController {

    @Autowired
    private ChannelService service;


    // 멤버가 가지고 있는 채널 목록이 필요한데 show가 갖고 있는 걸로는 충분치 않아
    // SELECT * FROM channel WHERE id=? => DAO 추가
    @GetMapping("/channel")
    public ResponseEntity<List<Channel>> showAll(){
        return ResponseEntity.status(HttpStatus.OK).body(service.showAll());
    }



    @GetMapping("/channel/{channelCode}")
    public ResponseEntity<Channel> show(@PathVariable int channelCode){
        return ResponseEntity.status(HttpStatus.OK).body(service.show(channelCode));
    }

    @PostMapping("/channel")
    public ResponseEntity<Channel> create(@RequestBody Channel channel){
        return ResponseEntity.status(HttpStatus.OK).body(service.create(channel));
    }

    @PutMapping("/channel")
    public ResponseEntity<Channel> update(@RequestBody Channel channel){
        Channel cha = service.update(channel);
        if(cha != null){
            return ResponseEntity.status(HttpStatus.OK).body(service.update(channel));
        }
        return null;
    }

    @DeleteMapping("/channel/{channelCode}")
    public ResponseEntity<Channel> delete(@PathVariable int channelCode){
       return ResponseEntity.status(HttpStatus.OK).body(service.delete(channelCode));
    }

}
