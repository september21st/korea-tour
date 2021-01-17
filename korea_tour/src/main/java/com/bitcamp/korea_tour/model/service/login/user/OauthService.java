package com.bitcamp.korea_tour.model.service.login.user;

import com.bitcamp.korea_tour.model.service.login.setting.SnsLoginType;

public interface OauthService {
	
	/**
     * 각 Social Login 페이지로 Redirect 처리할 URL Build
     * 사용자로부터 로그인 요청을 받아 Social Login Server 인증용 code 요청
     */
	String getOauthRedirectURL();
	
	 /**
     * API Server로부터 받은 code를 활용하여 사용자 인증 정보 요청
     * @param code API Server 에서 받아온 code
     * @return API 서버로 부터 응답받은 Json 형태의 결과를 string으로 반환
     */
    String requestAccessToken(String code);
    
    default SnsLoginType type() {
    	if(this instanceof KakaoOauth) {
    		return SnsLoginType.KAKAO;
    	}else if(this instanceof NaverOauth) {
    		return SnsLoginType.NAVER;
		}else if(this instanceof GoogleOauth) {
			return SnsLoginType.GOOGLE;
		}else {
			return null;
		}
    }
}