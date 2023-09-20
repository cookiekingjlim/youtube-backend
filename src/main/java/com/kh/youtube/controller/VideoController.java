package com.kh.youtube.controller;

import com.kh.youtube.domain.*;
import com.kh.youtube.repo.VideoDAO;
import com.kh.youtube.service.VideoCommentService;
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
public class VideoController {

    @Value("${spring.servlet.multipart.location}")// appication.properties에 있는 변수
    private String uploadPath;

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoCommentService videoComment;

    // 영상 전체 조회 GET - http://localhost:8000/api/video
    @GetMapping("/video")
    public ResponseEntity <List<Video>> videoList(){
        return ResponseEntity.status(HttpStatus.OK).body(videoService.showAll());
    }

    // 영상 추가 POST - http://localhost:8000/api/video
    @PostMapping("/video")
    public ResponseEntity<Video> createVideo(MultipartFile video, MultipartFile image, String title, String desc, String categoryCode) {
//       폼데이터로 받는 건 앞에 @Request...숨겨져 있어
//      video_title, video_desc, video_url, video_photo, category_code
//        log.info("video : " + video);
//        log.info("image : " + image);
//        log.info("title : " + title);
//        log.info("desc : " + desc);
//        log.info("categoryCode : " + categoryCode);

        //업로드 처리
        //비디오의 실제 파일 이름
        String originalVideo = video.getOriginalFilename();
        log.info("original : " + originalVideo);
        String realVideo = originalVideo.substring(originalVideo.lastIndexOf("\\")+1);
        log.info("realVideo : " + realVideo);

        //이미지의 실제 파일 이름
        String originalImg = image.getOriginalFilename();
        String realImg = originalImg.substring(originalImg.lastIndexOf("\\")+1);

        //UUID
        String uuid = UUID.randomUUID().toString();

        //실제로 저장할 파일명(위치 포함)
        String saveVideo = uploadPath + File.separator + uuid + "_" + realVideo;
        Path pathVideo = Paths.get(saveVideo);

        String saveImg = uploadPath + File.separator + uuid + "_" + realImg;
        Path pathImg = Paths.get(saveImg);

        try {
            video.transferTo(pathVideo);
            image.transferTo(pathImg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // saveVideo, saveImg, title, desc, categoryCode
        Video vo = new Video();
        vo.setVideoUrl(saveVideo);
        vo.setVideoPhoto(saveImg);
        vo.setVideoTitle(title);
        vo.setVideoDesc(desc);

        Category category = new Category();
        category.setCategoryCode(Integer.parseInt(categoryCode));
        vo.setCategory(category);

        Channel channel = new Channel();
        channel.setChannelCode(1);
        vo.setChannel(channel);

        // Member는 필수 아니야 없어도 됨
        Member member = new Member();
        member.setId("user1");
        vo.setMember(member);

//        return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.OK).body(videoService.create(vo));
    }

    // 영상 수정 PUT - http://localhost:8000/api/video
    @PutMapping("/video")
    public ResponseEntity <Video> updateVideo(@RequestBody Video vo){
        return ResponseEntity.status(HttpStatus.OK).body(videoService.update(vo));
    }

    // 영상 삭제 DELETE - http://localhost:8000/api/video/1
    @DeleteMapping("/video/{id}")
    public ResponseEntity <Video> deleteVideo(@PathVariable int id){
        return ResponseEntity.status(HttpStatus.OK).body(videoService.delete(id));
    }

    // 영상 한 개 조회 GET - http://localhost:8000/api/video/1
    @GetMapping("/video/{id}")
    public ResponseEntity <Video> showVideo(@PathVariable int id){
        return ResponseEntity.status(HttpStatus.OK).body(videoService.show(id));
    }

    //영상 1개에 따른 댓글 전체 조회  GET - http://localhost:8000/api/video/1/comment
    //SELECT * FROM videoComment WHERE video_code = ?
    @GetMapping("video/{id}/comment")
    public ResponseEntity <List<VideoComment>> videoCommentList(@PathVariable int id){
        return ResponseEntity.status(HttpStatus.OK).body(videoComment.findByVideoCode(id));
    }
    // 좋아요 추가 POST - http://localhost:8000/api/video/like

    // 좋아요 취소 DELETE - http://localhost:8000/api/video/like/1

    // 댓글 추가 POST - http://localhost:8000/api/video/comment

    // 댓글 수정 PUT - http://localhost:8000/api/video/comment

    // 댓글 삭제 DELETE - http://localhost:8000/api/video/comment/1

    // 댓글 좋아요 추가 POST - http://localhost:8000/api/video/comment/like

    // 댓글 좋아요 취소 DELETE - http://localhost:8000/api/video/comment/like/1
}
