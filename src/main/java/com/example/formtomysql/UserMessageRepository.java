package com.example.formtomysql.repository;

import com.example.formtomysql.entity.UserMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMessageRepository extends JpaRepository<UserMessage, Long> {
}

