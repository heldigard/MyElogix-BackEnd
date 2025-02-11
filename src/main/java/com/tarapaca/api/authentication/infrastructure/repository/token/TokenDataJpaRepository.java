package com.tarapaca.api.authentication.infrastructure.repository.token;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tarapaca.api.generics.infrastructure.repository.GenericEntity.GenericEntityRepository;

@Repository
public interface TokenDataJpaRepository extends GenericEntityRepository<TokenData> {
        @Query("SELECT t FROM TokenData t " +
                        "WHERE t.user.id = :userId " +
                        "AND t.isRevoked = false")
        Optional<TokenData> findValidToken(@Param("userId") Long userId);

        @Query("SELECT t FROM TokenData t " +
                        "WHERE t.user.id = :userId")
        Optional<TokenData> findByUserId(@Param("userId") Long userId);

        Optional<TokenData> findByToken(byte[] accessToken);

        Optional<TokenData> findByRefreshToken(byte[] refreshToken);

        void deleteByToken(byte[] accessToken);

        void deleteByRefreshToken(byte[] refreshToken);

        @Query("SELECT t FROM TokenData t WHERE t.updatedAt < :expirationDate")
        List<TokenData> findExpiredTokens(@Param("expirationDate") Instant expirationDate);

        @Modifying
        @Query("UPDATE TokenData t SET t.isRevoked = true WHERE t.user.id = :userId")
        void revokeAllUserTokens(@Param("userId") Long userId);
}
