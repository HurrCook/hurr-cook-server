package com.github.hurrcook.domain.auth.service;

import com.github.hurrcook.domain.auth.dto.response.KakaoTokenResponse;
import com.github.hurrcook.domain.auth.dto.response.KakaoUserInfoResponse;
import com.github.hurrcook.domain.auth.dto.response.TokenResponse;
import com.github.hurrcook.domain.auth.exception.AuthExceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final RestTemplate restTemplate;

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
    public String getKakaoLoginUrl(){
        return UriComponentsBuilder.fromHttpUrl(KAKAO_AUTH_URL)
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", "code")
                .toUriString();
    }


    /* 인가 코드로 토큰 발급 및 사용자 로그인 처리 */
    public TokenResponse kakaoLogin(String authorizeCode){
        // 카카오로부터 토큰 발급 받음
        KakaoTokenResponse kakaoTokenResponse = getTokenFromKakao(authorizeCode);
        // 토큰으로 유저 정보 받음
        KakaoUserInfoResponse kakaoUserInfoResponse = getUserInfoFromKakao(kakaoTokenResponse.accessToken());

        //TODO: 사용자 로그인 처리

        return new TokenResponse(kakaoTokenResponse.accessToken(), kakaoTokenResponse.refreshToken());
    }



    /*          내부 메서드          */


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
