package com.example.capstone.entity.taboo;

import com.example.capstone.entity.common.EntityDate;
import com.example.capstone.entity.pill.Pill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Taboo extends EntityDate {
    // 병용 금기

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Pill pillA;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Pill pillB;

    @Column(nullable = false)
    private String content; // 내용
}
