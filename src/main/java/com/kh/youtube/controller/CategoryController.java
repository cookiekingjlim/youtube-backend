package com.kh.youtube.controller;

import com.kh.youtube.domain.Category;
import com.kh.youtube.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/*")   // api 사용시 주소는 api를 작성해줘야하므로
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping("/category")
    public ResponseEntity<List<Category>> showAll() { //제너릭으로 명시하자
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.showAll());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();   // body(null) = build()처리
        }

    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Category> show(@PathVariable int id){ // 경로에서 가져와서 PathVariable
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.show(id));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/category")
    public ResponseEntity<Category> create(@RequestBody Category category) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.create(category ));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        }

    }

    @PutMapping("/category")
    public ResponseEntity<Category> update(@RequestBody Category category){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.update(category));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<Category> delete(@PathVariable int id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.delete(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }




}
