package org.leafall.authservice.repository;

import org.leafall.authservice.entity.TokenEntity;
import org.leafall.authservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    Optional<TokenEntity> findByToken(String token);
    List<TokenEntity> findAllByCreatedAtLessThan(long time);
    List<TokenEntity> findAllByUser(UserEntity user);
}
