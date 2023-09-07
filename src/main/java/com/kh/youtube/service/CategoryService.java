package com.kh.youtube.service;

import com.kh.youtube.domain.Category;
import com.kh.youtube.repo.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryDAO dao;

    public List<Category> showAll(){
        return dao.findAll();
    }

    public Category show(int code){
        return dao.findById(code).orElse(null); // 객체?로 반환해야하는데 코드로 반환해서 오류남->지정한 게 없으면 orElse로 null반환하겠다 설정해버려
    }

    public Category create(Category category){  //생성하는 거니까 Category 그 자체로 넘겨
        return dao.save(category);
    }

    public Category update(Category category){
        return dao.save(category);
    }

    public Category delete(int code){
        Category data = dao.findById(code).orElse(null);    // delete는 반환 타입이 void이므로 code가지고 findById에 넣어서 데이터 넘기면 됨
        dao.delete(data);
        return data;
    }

}
