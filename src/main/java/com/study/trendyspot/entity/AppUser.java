package com.study.trendyspot.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email", length = 255, unique = true)
    private String email;

    @Column(name = "nickname", length = 50)
    private String nickname;

    @Builder
    private AppUser(String email, String nickname){
        this.email = email;
        this.nickname = nickname;
    }
}
