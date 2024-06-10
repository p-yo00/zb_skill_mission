package com.example.zb_skill_mission.config;

import io.jsonwebtoken.Jwts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.crypto.SecretKey;

@Configuration
@EnableJpaAuditing
public class AppConfig {

    @Bean
    public SecretKey secretKey() {
        return Jwts.SIG.HS256.key().build();
    }
}
