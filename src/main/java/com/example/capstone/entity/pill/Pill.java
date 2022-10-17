package com.example.capstone.entity.pill;

import com.example.capstone.entity.common.EntityDate;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pill extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 품목명

    @Column(nullable = false)
    private String company; // 업체명

    // 허가일자 일단 제외

    @Column(nullable = false)
    private boolean isGeneral; // true = 일반, false = 전문

    // 원료 성분은 테이블 쪼개야할듯

    @Column(nullable = false)
    private String effect; // 효능 효과

    @Column(nullable = false)
    private String usageCapacity; // 용법 용량

}
