package com.example.zb_skill_mission.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

public class Auth {
    @Data
    public static class SignIn {
        @NotBlank
        private String userId;
        @NotBlank
        private String password;
    }

    @Data
    public static class SignUp {
        @NotBlank
        private String email;
        @NotBlank
        private String password;
        private String username;
        private String phone_number;
        private String address;
        private double lat;
        private double lnt;
        private List<String> roles;
    }
}
