package com.abg.amp.repository;

import com.abg.amp.entity.AuthToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, LocalDateTime> {
    Optional<AuthToken> findTopByOrderByUpdateTimeDesc();

    @Modifying
    @Transactional
    //@Query("UPDATE AuthToken authToken SET authToken.updateTime=:newTime, authToken.accessToken=:accessToken " + "WHERE authToken.updateTime=:oldTime")
    @Query(value = "update auth_token set update_time =:newTime, access_token =:accessToken, expires_in =:expiresIn where update_time =:oldTime", nativeQuery = true)
    int updateExistingRow(@Param("oldTime") LocalDateTime oldTime,@Param("newTime") LocalDateTime newTime, @Param("accessToken") String accessToken, @Param("expiresIn") int expiresIn);
}