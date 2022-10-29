package com.example.capstone.entity.contraindicate;

import com.example.capstone.entity.common.EntityDate;
import com.example.capstone.entity.pill.Pill;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class contraindicate extends EntityDate {
    // 병용 금기

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Pill pill_a;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Pill pill_b;

    @Column(nullable = false)
    private String symptom; // 내용
}
