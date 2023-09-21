package com.kh.youtube.controller;

import com.kh.youtube.domain.*;
import com.kh.youtube.repo.VideoDAO;
import com.kh.youtube.service.VideoCommentService;
import com.kh.youtube.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class VideoController {

    @Value("${spring.servlet.multipart.location}")// appication.properties에 있는 변수
    private String uploadPath;

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoCommentService videoComment;

    // 영상 전체 조회 GET - http://localhost:8000/api/video
    @GetMapping("/video")
    public ResponseEntity <List<Video>> videoList(@RequestParam(name = "page", defaultValue = "1") int page){    // 페이지 처리 안 할 시 디폴트값 1, 페이지 넘버 관련해서 받아와
        // 정렬 추가 => 비디오 코드 기준으로 오름차순!
        Sort sort = Sort.by("videoCode").descending();
        // 한 페이지에 영상 10개(몇번째 페이지, 영상 개수, 정렬)
        Pageable pageable = PageRequest.of(page-1,10, sort);    // 우리는 0부터 시작이니까
        Page<Video> result = videoService.showAll(pageable);

        log.info("Total Pages : " + result.getTotalPages());    // 총 페이지 가져와
        log.info("Total Count : " + result.getTotalElements());     // 전체 개수에 해당
        log.info("Page Number : " +  result.getNumber());   // 현재 페이지 번호
        log.info("Page Size : " +  result.getSize());// 페이지당 데이터 개수
        log.info("Next Page : " + result.hasNext());    // 다음 페이지의 존재여부 확인
        log.info("First Page : " + result.isFirst()); // 시작 페이지 여부

//         return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.OK).body(result.getContent());  // 리스트로 받았으므로 이걸로 바꿔
    }

    // 영상 추가 POST - http://localhost:8000/api/video
    @PostMapping("/video")
    public ResponseEntity<Video> createVideo(MultipartFile video, MultipartFile image, String title, @RequestParam(name="desc", required = false) String desc, String categoryCode) {
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
        vo.setVideoUrl(uuid + "_" + realVideo); //상대경로라 파일명만 지정
        vo.setVideoPhoto(uuid + "_" + realImg);
        vo.setVideoTitle(title);
        vo.setVideoDesc(desc);

        Category category = new Category();
        category.setCategoryCode(Integer.parseInt(categoryCode));
        vo.setCategory(category);

        Channel channel = new Channel();
        channel.setChannelCode(22);
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
