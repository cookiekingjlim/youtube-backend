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
public class Video {
    @Id
    @Column(name = "VIDEO_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "videoSequence")
    @SequenceGenerator(name = "videoSequence",sequenceName = "SEQ_VIDEO",allocationSize = 1)
    private int videoCode;
    @Column(name="VIDEO_TITLE")
    private String videoTitle;
    @Column(name = "VIDEO_DESC")
    private String videoDesc;
    @Column(name="VIDEO_DATE")
    private Date videoDate;
    @Column(name = "VIDEO_VIEWS")
    private int videoViews;
    @Column(name="VIDEO_URL")
    private String videoUrl;
    @Column(name = "VIDEO_PHOTO")
    private String videoPhoto;

    @JoinColumn(name="CATEGORY_CODE")
    @ManyToOne
    private Category category;
    @JoinColumn(name="channel_code")
    @ManyToOne
    private Channel channel;
    @JoinColumn(name="ID")
    @ManyToOne
    private Member member;

}
