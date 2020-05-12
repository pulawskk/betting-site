package com.pulawskk.bettingsite.repositories;

import com.pulawskk.bettingsite.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    @Modifying
    @Query(value = "UPDATE wallet SET balance = (SELECT balance FROM wallet WHERE user_id = ?2) + ?1 WHERE user_id = ?2", nativeQuery = true)
    void addWinningToCurrentBalance(BigDecimal amountWinning, Long userId);

    @Query(value = "SELECT balance FROM wallet WHERE user_id = ?1", nativeQuery = true)
    BigDecimal findBalanceForUser(Long userId);

    @Modifying
    @Query(value = "UPDATE wallet SET balance = (SELECT balance FROM wallet WHERE user_id = ?2) + ?1 WHERE user_id = ?2", nativeQuery = true)
    void updateBalanceForUser(double newAmount, Long userId);

    @Modifying
    @Query(value = "UPDATE wallet SET balance = (SELECT balance FROM wallet WHERE user_id = ?2) - ?1 WHERE user_id = ?2", nativeQuery = true)
    void updateBalanceForPlaceBet(double subtractAmount, Long userId);
}
