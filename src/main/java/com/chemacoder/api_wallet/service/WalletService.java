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
}