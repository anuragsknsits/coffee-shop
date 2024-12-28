package com.coffeeshop.auth.repository;

import com.coffeeshop.auth.entity.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDTO, Long> {
    Optional<UserDTO> findByEmail(String email);
}

