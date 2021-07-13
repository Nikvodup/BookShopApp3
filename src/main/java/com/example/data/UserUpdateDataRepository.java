package com.example.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserUpdateDataRepository extends JpaRepository<UserUpdateData, Integer> {

    UserUpdateData findUserUpdateDataByToken(String token);

}
