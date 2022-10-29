package com.example.capstone.entity.member;

import com.example.capstone.entity.common.EntityDate;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Member extends EntityDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String username;

    @Column(nullable = false)
    private String pw;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Builder
    public Member(String username, String pw) {
        this.username = username;
        this.pw = pw;
        this.authority = Authority.ROLE_USER;
    }

}
