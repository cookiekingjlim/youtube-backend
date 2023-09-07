package com.kh.youtube.repo;

import com.kh.youtube.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDAO extends JpaRepository<Category, Integer> {  // <>제너릭으로 Entity 타입과 Primary값의 데이터 타입 명시

}
