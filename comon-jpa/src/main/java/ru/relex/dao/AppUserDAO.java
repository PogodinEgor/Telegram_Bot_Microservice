package ru.relex.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.relex.entity.AppUser;

import java.util.Optional;

@Repository
public interface AppUserDAO extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByTelegrammUserId(Long id);
    Optional<AppUser> findById(Long id);
    Optional<AppUser> findByEmail(String id);

}
