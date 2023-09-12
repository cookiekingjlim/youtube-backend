package com.kh.youtube.service;

import com.kh.youtube.domain.Subscribe;
import com.kh.youtube.repo.SubscribeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscribeService {
    @Autowired
    private SubscribeDAO dao;

    public Subscribe create(Subscribe subscribe){
        return dao.save(subscribe);
    }
    public Subscribe update(Subscribe subscribe){
        Subscribe sub = dao.findById(subscribe.getSubsCode()).orElse(null);
        if(sub != null){
            return dao.save(subscribe);
        }
        return null;
    }
    public Subscribe delete(int id){
        Subscribe sub = dao.findById(id).orElse(null);
        dao.delete(sub);
        return sub;
    }

    public List<Subscribe> showAll(){
        return dao.findAll();
    }

    public Subscribe show(int id){
    return dao.findById(id).orElse(null);
    }

    public List<Subscribe> findByMemberId(String id){
        return dao.findByMemberId(id);
    }
}
