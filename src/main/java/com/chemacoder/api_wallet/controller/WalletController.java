package com.chemacoder.api_wallet.controller;

import com.chemacoder.api_wallet.entity.Wallet;
import com.chemacoder.api_wallet.repository.WalletRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {

    private final WalletRepository walletRepository;

    public WalletController(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    /**
     * Creates a new wallet with an initial balance of zero.
     *
     * @param walletRequest The wallet data containing the user's email.
     * @return ResponseEntity containing the created Wallet and HTTP status 200 OK.
     */
    @PostMapping
    public ResponseEntity<Wallet> createWallet(@RequestBody Wallet walletRequest) {
        walletRequest.setBalance(BigDecimal.ZERO);
        walletRequest.setCreatedAt(LocalDateTime.now());

        Wallet savedWallet = walletRepository.save(walletRequest);

        return ResponseEntity.ok(savedWallet);
    }
    /**
     * Retrieves a wallet by its unique ID.
     *
     * @param id The UUID of the wallet to retrieve.
     * @return ResponseEntity containing the Wallet if found, or 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Wallet> getWalletById(@PathVariable UUID id) {
        return walletRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}