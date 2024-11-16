package com.nuneddine.server.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "memberId")
    private Long id;

    @Column(unique = true)
    private Long kakaoId;
    private String username;
    private int build;
    private int chance;
    private int point;
    private String image;

    @Builder
    public Member(Long kakaoId, String username, int build, int chance, int point, String image) {
        this.kakaoId = kakaoId;
        this.username = username;
        this.build = build;
        this.chance = chance;
        this.point = point;
        this.image = image;
    }

    public void updateImage(String image) {
        this.image = image;
    }

    public void updateUsername(String username) {
        this.username = username;
    }

    public void addBuild() {
        this.build += 1;
    }
}
