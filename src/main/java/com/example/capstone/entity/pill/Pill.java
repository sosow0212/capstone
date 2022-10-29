package com.example.capstone.entity.pill;

import com.example.capstone.entity.common.EntityDate;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private String item_seq;

    @Column(nullable = false)
    private String item_name;

    @Column(nullable = false)
    private String entp_name;

    @Enumerated(EnumType.STRING)
    private PillCode etc_otc_code;

    @Column(nullable = false)
    private String meterial_name;

    @Column(nullable = false)
    private String ee_doc_data;

}
