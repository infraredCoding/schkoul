package com.imraninfrared.schkoul.repository;

import com.imraninfrared.schkoul.domain.models.SchkoulUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SchkoulUser, Long> {
    Optional<SchkoulUser> findByUsername(String username);
}
