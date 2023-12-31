package com.kh.youtube.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert  // 추가할 때 default값이 자동으로 들어가는 어노테이션
public class Member {

    @Id
    private String id;

    @Column
    private String password;
    @Column
    private String name;
    @Column
    private String authority;

}
