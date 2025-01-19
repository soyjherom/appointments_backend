package com.jherom.ventures.appointments_backend.repositories;

import com.jherom.ventures.appointments_backend.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByPhoneHash(String phoneHash);
    User findByEmailHash(String emailHash);
    Page<User> findByNameContaining(String name, Pageable pageable);
}
