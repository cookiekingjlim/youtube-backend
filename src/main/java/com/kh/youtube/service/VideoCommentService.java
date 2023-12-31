package com.kh.youtube.service;

import com.kh.youtube.domain.VideoComment;
import com.kh.youtube.repo.VideoCommentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VideoCommentService {
    @Autowired
    private VideoCommentDAO dao;

    public VideoComment create(VideoComment vo){
        return dao.save(vo);
    }

    public VideoComment update(VideoComment vo) {
        VideoComment target = dao.findById(vo.getCommentCode()).orElse(null);
        if (target != null) {
            return dao.save(vo);
        }
        return null;
    }

    public VideoComment delete(int id){
        VideoComment target = dao.findById(id).orElse(null);
        dao.delete(target);
        return target;
    }

    public VideoComment show(int id){
        return dao.findById(id).orElse(null);
    }

    public List<VideoComment> showAll(){
        return dao.findAll();
    }

    public List<VideoComment> findByVideoCode(int code) {
        return dao.findByVideoCode(code);
    }
}

