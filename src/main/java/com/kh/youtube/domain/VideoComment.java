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
public class VideoComment {
    @Id
    @Column(name="COMMENT_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "videoCommSequence")
    @SequenceGenerator(name = "videoCommSequence",sequenceName = "SEQ_VIDEO_COMMENT",allocationSize = 1)
    private int commentCode;
    @Column(name="COMMENT_DESC")
    private String commentDesc;
    @Column(name="COMMENT_DATE")
    private Date commentDate;
    @Column(name="COMMENT_PARENT")
    private int commentParent;

    @JoinColumn(name = "VIDEO_CODE")
    @ManyToOne
    private Video video;
    @JoinColumn(name = "ID")
    @ManyToOne
    private Member member;

}
