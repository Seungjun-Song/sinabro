package com.HP50.be.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); //내 서버가 응답을 할 때 json을 자바스크립트에서 처리할 수 있게 할지를 설정하는 것
        config.addAllowedOriginPattern("https://k10e103.p.ssafy.io"); // 우리 도메인에서 오는 요청만 허용
        config.addAllowedHeader("192.168.30.*");
        config.addAllowedOriginPattern("http://localhost:5173"); // 우리 도메인에서 오는 요청만 허용
        config.addExposedHeader("*");
        config.addAllowedHeader("*"); // 모든 header에 응답을 허용하겠다.
        config.addAllowedMethod("*"); // 모든 post,get,put,delete,patch 요청을 허용하겠다.
        source.registerCorsConfiguration("/api/**", config); // 해당 주소로 들어오는 모든 요청은 config 를 따라주겠다
        return new CorsFilter(source);
    }

}