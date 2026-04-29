package com.chemacoder.api_wallet.service;

import com.chemacoder.api_wallet.entity.Wallet;
import com.chemacoder.api_wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    /**
     * Creates a new wallet with a default zero balance.
     *
     * @param walletRequest The wallet data containing the user's email.
     * @return The persisted Wallet entity.
     */
    public Wallet createWallet(Wallet walletRequest) {
        walletRequest.setBalance(BigDecimal.ZERO);
        walletRequest.setCreatedAt(LocalDateTime.now());

        return walletRepository.save(walletRequest);
    }

    /**
     * Retrieves a wallet by its unique identifier.
     *
     * @param id The UUID of the wallet.
     * @return An Optional containing the Wallet if found, or empty if not.
     */
    public Optional<Wallet> getWalletById(UUID id) {
        return walletRepository.findById(id);
    }

    /**
     * Deposits a specific amount into a wallet.
     *
     * @param id The UUID of the wallet.
     * @param amount The amount to deposit (must be greater than zero).
     * @return The updated Wallet.
     * @throws IllegalArgumentException if the wallet is not found or the amount is invalid.
     */
    public Wallet deposit(UUID id, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero");
        }

        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found"));

        BigDecimal newBalance = wallet.getBalance().add(amount);
        wallet.setBalance(newBalance);

        return walletRepository.save(wallet);
    }
}