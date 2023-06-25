package com.jwt.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "user_table")
public class User implements UserDetails {

    @Id
    private String userId;

    private String name;

    private String email;

    private String password;

    private String about;

    @OneToOne(mappedBy = "user") // means mapped by user property of RefreshToken class
    @JsonIgnore
    private RefreshToken refreshToken;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getUsername() {
        return this.email;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }
}