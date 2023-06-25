package com.jwt.example.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
//Entity to store Refresh Token in database
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tokenId; // it cannot be updated

    // unique(primary key)
    private String refreshToken; // it can be updated

    private Instant expiry;

    @OneToOne
    private User user;

}
