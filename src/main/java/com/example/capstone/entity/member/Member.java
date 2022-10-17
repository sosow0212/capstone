package com.example.capstone.entity.member;

import com.example.capstone.entity.common.EntityDate;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private String password;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Builder
    public Member(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.authority = Authority.ROLE_USER;
    }

}
