package com.kh.youtube.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class Channel {

    @Id
    @Column(name="channel_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "channelSeq")
    @SequenceGenerator(name = "channelSeq", sequenceName = "SEQ_CHANNEL",allocationSize = 1)
    private int channelCode;

    @Column(name="channel_name")
    private String channelName;
    @Column(name="channel_photo")
    private String channelPhoto;
    @Column(name="channel_desc")
    private String channelDesc;
    @Column(name="channel_date")
    private Date channelDate;

    @ManyToOne //Channel 엔티티와 Member 엔티티를 다대일 관계 설정임을 명시, 한 멤버가 여러 채널을 가지고 있음
    @JoinColumn(name="id")   // 조인한 컬럼 명시해야함(foreign key/primary key)    // 외래키 생성 or Member 엔티티의 기본키와 매핑
    private Member member;

}
