package com.kh.youtube.service;

import com.kh.youtube.domain.Member;
import com.kh.youtube.repo.MemberDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j  // 로그 찍어보는 어노테이션
public class MemberService {
    @Autowired
    private MemberDAO dao;

    public List<Member> showAll(){
        return dao.findAll();    //전체 리스트에 관련된 메소드->findAll // SELECT * FROM MEMBER
    }

    public Member show(String id){
        return dao.findById(id).orElse(null); // SELECT * FROM MEMBER WHERE id=? 에 해당하는 문법
    }

    // INSERT INTO MEMBER(ID, PASSWORD, NAME, AUTHORITY)
    // VALUES(?, ?, ?, 'ROLE_USER')
    public Member create(Member member){
        log.info("member : " + member);
        return dao.save(member);
    }

    //UPDATE MEMBER SET ID=?, PASSWORD=?, NAME=?, AUTHORITY=?
    // WHERE ID=?
    public Member update(Member member){
        Member target = dao.findById(member.getId()).orElse(null);
        if(target != null){
            return dao.save(member);    //save: 해당 정보가 없으면 insert, 없으면 update하는 메소드
        }
       return null;
    }

    // DELETE FROM MEMBER WHERE ID=?
    public Member delete(String id){
        Member target = dao.findById(id).orElse(null);
        dao.delete(target);
        return target;
    }
}
