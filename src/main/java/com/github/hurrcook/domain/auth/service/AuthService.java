package com.github.hurrcook.domain.auth.service;

import com.github.hurrcook.domain.auth.dto.response.KakaoTokenResponse;
import com.github.hurrcook.domain.auth.dto.response.KakaoUserInfoResponse;
import com.github.hurrcook.domain.auth.dto.response.TokenResponse;
import com.github.hurrcook.domain.auth.exception.AuthExceptions;
import com.github.hurrcook.domain.cookware.entity.Cookware;
import com.github.hurrcook.domain.user.entity.User;
import com.github.hurrcook.domain.user.repository.UserRepository;
import com.github.hurrcook.global.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
    private final RestTemplate restTemplate;
    private final UserRepository userRepository;

    @Value("${app.oauth.kakao.client-id}")
    private String clientId;

    @Value("${app.oauth.kakao.redirect-uri}")
    private String redirectUri;

    @Value("${app.oauth.kakao.client-secret}")
    private String clientSecret;

    private final String KAKAO_AUTH_URL = "https://kauth.kakao.com/oauth/authorize";
    private final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";



    /* 로그인 페이지로의 리다이렉트 Url 반환 */
    @Transactional(readOnly = true)
    public String getKakaoLoginUrl(){
        return UriComponentsBuilder.fromHttpUrl(KAKAO_AUTH_URL)
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", "code")
                .toUriString();
    }


    /* 인가 코드로 토큰 발급 및 사용자 로그인 처리 */
    @Transactional
    public TokenResponse kakaoLogin(String authorizeCode){
        // 카카오로부터 토큰 발급 받음
        KakaoTokenResponse kakaoTokenResponse = getTokenFromKakao(authorizeCode);
        // 토큰으로 유저 정보 받음
        KakaoUserInfoResponse kakaoUserInfoResponse = getUserInfoFromKakao(kakaoTokenResponse.getAccessToken());
        // 사용자 로그인 (최초 시 회원가입)
        User user = login(kakaoUserInfoResponse);

        // JWT 토큰 생성
        //TODO: 리프레시 토큰 관련 추가 후 반환 값에 반영
        String accessToken = jwtUtil.createToken(user);
//        String refreshToken = jwtUtil.

        return TokenResponse.of(kakaoTokenResponse.getAccessToken(), kakaoTokenResponse.getRefreshToken());
    }



    /*          내부 메서드          */

    /* 서비스 회원가입/로그인 처리 */
    private User login(KakaoUserInfoResponse kakaoUserInfoResponse){
        Long kakaoId = kakaoUserInfoResponse.getId();
        String nickname = kakaoUserInfoResponse.getKakaoAccount().getProfile().getNickname();

        return userRepository.findByKakaoId(kakaoId)
                .map(existingUser-> { // 유저가 있으면 로그인 처리, 변경 사항을 업데이트
                    existingUser.setName(nickname);

                    return existingUser;
                })
                .orElseGet(()-> { // 비어있으면 회원가입 처리
                    User user = User.builder()
                            .kakaoId(kakaoId)
                            .name(nickname)
                            .build();

                    // 조리도구 엔티티 생성 및 유저 엔티티 관계 설정
                    Cookware cookware = Cookware.builder().build();
                    user.setCookware(cookware);
                    cookware.setUser(user);

                    return userRepository.save(user);
                });

    }


    /* 카카오 서버에 토큰 발급 요청 */
    private KakaoTokenResponse getTokenFromKakao(String authorizeCode){

        // header 생성 및 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // body 생성 및 설정
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code"); // 고정값
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", authorizeCode);
        body.add("client_secret",clientSecret);

        // Http Object 생성
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        // 카카오 서버에 토큰 발급 요청
        try {
            ResponseEntity<KakaoTokenResponse> response = restTemplate.exchange(
                    KAKAO_TOKEN_URL, HttpMethod.POST, request, KakaoTokenResponse.class);

            if (response.getStatusCode()== HttpStatus.OK){
                return response.getBody();
            } else {
                throw AuthExceptions.KAKAO_TOKEN_ISSUE_FAILED.toApiException();
            }
        } catch (Exception e) {
            throw AuthExceptions.KAKAO_TOKEN_ISSUE_FAILED.toApiException();
        }
    }


    /* 카카오 서버에 사용자 정보 요청 */
    private KakaoUserInfoResponse getUserInfoFromKakao(String accessToken){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        // 헤더에 토큰 담아 GET 요청 시도
        try {
            ResponseEntity<KakaoUserInfoResponse> response = restTemplate.exchange(
                    KAKAO_USER_INFO_URL, HttpMethod.GET, request, KakaoUserInfoResponse.class);

            if (response.getStatusCode()== HttpStatus.OK){
                return response.getBody();
            } else {
                throw AuthExceptions.KAKAO_USERINFO_REQUEST_FAILED.toApiException();
            }
        } catch (Exception e) {
            throw AuthExceptions.KAKAO_USERINFO_REQUEST_FAILED.toApiException();
        }
    }

}
