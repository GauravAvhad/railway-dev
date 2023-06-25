package com.jwt.example.services;

import com.jwt.example.entities.RefreshToken;
import com.jwt.example.entities.User;
import com.jwt.example.repositories.RefreshTokenRepository;
import com.jwt.example.repositories.UserRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

//    public long refreshTokenValidity = 5 * 60 * 60 * 1000; // 5 hours
    public long refreshTokenValidity = 2 * 60 * 1000; // 2 minutes

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public RefreshToken createRefreshToken(String userName){

        User user = userRepository.findByEmail(userName).get();
        RefreshToken refreshToken1 = user.getRefreshToken();

        if(refreshToken1 == null){
            //UUID.randomUUID().toString() is unique value for token
             refreshToken1 = RefreshToken.builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .expiry(Instant.now().plusMillis(refreshTokenValidity)) // token will expire after 5 hours
                    .user(userRepository.findByEmail(userName).get())
                    .build();
        }
        else
        {
            //update refreshtoken by updating its expiry to more 5 hours
            refreshToken1.setExpiry(Instant.now().plusMillis(refreshTokenValidity));
        }

        user.setRefreshToken(refreshToken1);


        //save to database
        refreshTokenRepository.save(refreshToken1);

        return refreshToken1;
    }

   /* when we have to generate new JWT Token using refresh token that time
     the following method is used*/
    public RefreshToken verifyRefreshToken(String refreshToken){

        // Gets Refresh Token from database
        RefreshToken refreshTokenOb = refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new RuntimeException("Given Token does not exists in db"));

        // if  refreshTokenOb.getExpiry().compareTo(Instant.now()) == -1
        // then refresh token has expired
        if(refreshTokenOb.getExpiry().compareTo(Instant.now()) < 0)
        {
            refreshTokenRepository.delete(refreshTokenOb);
            throw new RuntimeException("Refresh Token Expired !!");
        }
        return refreshTokenOb;
    }
}
