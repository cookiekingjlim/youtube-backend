package com.kh.youtube.controller;

import com.kh.youtube.domain.Channel;
import com.kh.youtube.domain.Member;
import com.kh.youtube.domain.Subscribe;
import com.kh.youtube.domain.Video;
import com.kh.youtube.service.ChannelService;
import com.kh.youtube.service.SubscribeService;
import com.kh.youtube.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/*")
@Slf4j
public class ChannelController {

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    @Autowired
    private ChannelService channel;
    @Autowired
    private SubscribeService subscribe;
    @Autowired
    private VideoService video;

    // 채널 조회 GET - http://localhost:8000/api/channel/1
    @GetMapping("/channel/{id}/")
    public ResponseEntity<Channel> showChannel(@PathVariable int id){
        return ResponseEntity.status(HttpStatus.OK).body(channel.show(id));
    }

    // 채널에 있는 영상 조회 GET - http://localhost:8000/api/channel/1/video
    @GetMapping("channel/{id}/video")
    public ResponseEntity<List<Video>> channelVideoList(@PathVariable int id){
       // SELECT * FROM video WHERE channel_code = ?
        return ResponseEntity.status(HttpStatus.OK).body(video.findByChannelCode(id));
    }

    // 채널 추가 POST - http://localhost:8000/api/channel
    @PostMapping("/channel")
    public ResponseEntity<Channel> createChannel(MultipartFile photo, String name, String desc ){
        String originalPhoto = photo.getOriginalFilename();
        String realPhoto = originalPhoto.substring(originalPhoto.lastIndexOf("\\")+1);

        String uuid = UUID.randomUUID().toString();

        String savePhoto = uploadPath + File.separator + uuid + "_" + realPhoto;
        Path pathPhoto = Paths.get(savePhoto);

        try {
            photo.transferTo(pathPhoto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        // 파일 업로드가 끝났으니 경로(savePhoto), name, desc, memberId(id) 필요함

        Channel vo = new Channel();
        vo.setChannelPhoto(savePhoto);
        vo.setChannelName(name);
        vo.setChannelDesc(desc);
        Member member = new Member();
        member.setId("user1");  // 나중에 처리할 거라 임시적으로 담아
        vo.setMember(member);

//        return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.OK).body(channel.create(vo));
    }

    // 채널 수정 PUT - http://localhost:8000/api/channel
    @PutMapping("/channel")
    public ResponseEntity<Channel> updateChannel(@RequestBody Channel vo){
        return ResponseEntity.status(HttpStatus.OK).body(channel.update(vo));
    }

    // 채널 삭제 DELETE - http://localhost:8000/api/channel/1
    @DeleteMapping("/channel/{id}")
    public ResponseEntity<Channel> deleteChannel(@PathVariable int id){
        return ResponseEntity.status(HttpStatus.OK).body(channel.delete(id));
    }

    // 내가 구독한 채널 조회 GET - http://localhost:8000/api/subscribe/user1
    @GetMapping("subscribe/{user}")
    public ResponseEntity<List<Subscribe>> subscribeList(@PathVariable String user){    // 유저의 id
        return ResponseEntity.status(HttpStatus.OK).body(subscribe.findByMemberId(user));
    }


    // 채널 구독 POST - http://localhost:8000/api/subscribe
    @PostMapping("/subscribe")
    public ResponseEntity<Subscribe> createSubscribe(@RequestBody Subscribe vo){
        return ResponseEntity.status(HttpStatus.OK).body(subscribe.create(vo));
    }

    // 채널 구독 취소 DELETE - http://localhost:8000/api/subscribe/1
    @DeleteMapping("/subscribe/{id}")
    public ResponseEntity<Subscribe> deleteSubs(@PathVariable int id){
        return ResponseEntity.status(HttpStatus.OK).body(subscribe.delete(id));
    }



//     멤버가 가지고 있는 채널 목록이 필요한데 show가 갖고 있는 걸로는 충분치 않아
//     SELECT * FROM channel WHERE id=? => DAO 추가
//    @GetMapping("/channel")
//    public ResponseEntity<List<Channel>> showAll(){
//        return ResponseEntity.status(HttpStatus.OK).body(service.showAll());
//    }
//
//
//
//    @GetMapping("/channel/{channelCode}")
//    public ResponseEntity<Channel> show(@PathVariable int channelCode){
//        return ResponseEntity.status(HttpStatus.OK).body(service.show(channelCode));
//    }
//
//    @PostMapping("/channel")
//    public ResponseEntity<Channel> create(@RequestBody Channel channel){
//        return ResponseEntity.status(HttpStatus.OK).body(service.create(channel));
//    }
//
//    @PutMapping("/channel")
//    public ResponseEntity<Channel> update(@RequestBody Channel channel){
//        Channel cha = service.update(channel);
//        if(cha != null){
//            return ResponseEntity.status(HttpStatus.OK).body(service.update(channel));
//        }
//        return null;
//    }
//
//    @DeleteMapping("/channel/{channelCode}")
//    public ResponseEntity<Channel> delete(@PathVariable int channelCode){
//       return ResponseEntity.status(HttpStatus.OK).body(service.delete(channelCode));
//    }

}
