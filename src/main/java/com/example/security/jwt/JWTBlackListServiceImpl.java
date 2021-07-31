package com.example.security.jwt;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JWTBlackListServiceImpl implements com.example.security.jwt.JWTBlackListService {

    private final com.example.security.jwt.JWTBlackListRepository jwtBlackListRepository;

    public JWTBlackListServiceImpl(com.example.security.jwt.JWTBlackListRepository jwtBlackListRepository) {
        this.jwtBlackListRepository = jwtBlackListRepository;
    }

    @Override
    public com.example.security.jwt.JWTBlackList getByToken(String token) {
        return this.jwtBlackListRepository.findJwtBlacklistByToken(token);
    }

    @Override
    public com.example.security.jwt.JWTBlackList saveToken(com.example.security.jwt.JWTBlackList jwtBlacklist) {
        return this.jwtBlackListRepository.save(jwtBlacklist);
    }

    @Override
    public void deleteOldToken() {
        this.jwtBlackListRepository.deleteOldTokens();
    }

    @Override
    public List<com.example.security.jwt.JWTBlackList> getOldTokens() {
        return this.jwtBlackListRepository.getOldTokens();
    }
}
