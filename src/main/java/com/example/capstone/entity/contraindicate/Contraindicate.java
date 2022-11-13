package com.example.capstone.entity.contraindicate;

import com.example.capstone.entity.common.EntityDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Contraindicate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pill_a")
    private String pillA;

    private String company_a;

    @Column(name = "pill_b")
    private String pillB;

    private String company_b;

    private String symptom;

    private int viewCount;

    public void addViewCount() {
        this.viewCount += 1;
    }

    @PrePersist
    public void prePersist() {
        this.viewCount = 0;
    }
}
