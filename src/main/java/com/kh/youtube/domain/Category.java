package com.kh.youtube.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @Column(name = "category_code") // 오라클 이름하고 맞춤
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "categorySequence") // sequencegenerator의 name과 맞추는 것
    @SequenceGenerator(name="categorySequence", sequenceName = "SEQ_CATEGORY",allocationSize = 1) // 여기 이름은 자유롭게 지정 가능, allocationSize : 1씩 증가 시키겠다.
    private int categoryCode;

    @Column(name = "category_name")
    private String categoryName;

}
