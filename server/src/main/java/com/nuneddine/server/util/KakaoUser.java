package com.nuneddine.server.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoUser {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    @Setter
    public static class KakaoAccount {
        private Profile profile;
    }

    @Getter
    @Setter
    public static class Profile {
        private String nickname;

        @JsonProperty("profile_image_url")
        private String image;
    }
}
