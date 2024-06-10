package com.example.zb_skill_mission.entity;

import com.example.zb_skill_mission.model.Auth;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String username;
    private String phoneNumber;
    private String address;
    private double lat;
    private double lnt;
    @ElementCollection(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns=@JoinColumn(name="user_id"))
    private List<String> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public static UserEntity toEntity(Auth.SignUp signUp) {
        return UserEntity.builder()
                .email(signUp.getEmail())
                .roles(signUp.getRoles())
                .password(signUp.getPassword())
                .username(signUp.getUsername())
                .lat(signUp.getLat())
                .lnt(signUp.getLnt())
                .address(signUp.getAddress())
                .phoneNumber(signUp.getPhone_number())
                .build();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
