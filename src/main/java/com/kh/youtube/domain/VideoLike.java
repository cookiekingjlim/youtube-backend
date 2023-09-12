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
public class VideoLike {
    @Id
    @Column(name = "V_LIKE_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "vLikeSequence")
    @SequenceGenerator(name = "vLikeSequence", sequenceName = "SEQ_VIDEO_LIKE",allocationSize = 1)
    private int vLikeCode;
    @Column(name="V_LIKE_DATE")
    private Date vLikeDate;

    @JoinColumn(name = "VIDEO_CODE")
    @ManyToOne
    private Video video;
    @JoinColumn(name = "ID")
    @ManyToOne
    private Member member;


}
