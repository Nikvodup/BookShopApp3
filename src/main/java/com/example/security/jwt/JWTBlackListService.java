package com.example.security.jwt;


import java.util.List;

public interface JWTBlackListService {

    JWTBlackList getByToken(String token);

    JWTBlackList saveToken(JWTBlackList jwtBlacklist);

    void deleteOldToken();

    List<JWTBlackList> getOldTokens();

}
