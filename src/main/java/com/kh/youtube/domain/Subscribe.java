package com.kh.youtube.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.Id;

import java.util.Date;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class Subscribe {
    @Id
    @Column(name="SUBS_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "subSequence")
    @SequenceGenerator(name = "subSequence",sequenceName = "SEQ_SUBSCRIBE",allocationSize = 1)
    private int subsCode;
    @Column(name="SUBS_DATE")
    private Date subsDate;

    @JoinColumn(name = "ID")
    @ManyToOne
    private Member member;
    @JoinColumn(name="channel_code")
    @ManyToOne
    private Channel channel;

}
