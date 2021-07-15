package com.example.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Book2UserRepository extends JpaRepository<Book2User, Integer> {

    Book2User findByUserIdAndBookId(Integer userId, Integer id);

    Book2User findByUserIdAndBookIdAndBook2Type_TypeStatusIn(Integer userId, Integer id, List<Book2Type.TypeStatus> typeStatus);

//    UserTemp findById(Integer Id);

}

