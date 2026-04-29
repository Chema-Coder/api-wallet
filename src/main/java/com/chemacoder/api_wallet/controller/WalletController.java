package com.chemacoder.api_wallet.controller;

import com.chemacoder.api_wallet.dto.DepositRequest;
import com.chemacoder.api_wallet.entity.Wallet;
import com.chemacoder.api_wallet.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    /**
     * Creates a new wallet.
     *
     * @param walletRequest The wallet data containing the user's email.
     * @return ResponseEntity containing the created Wallet and HTTP status 200 OK.
     */
    @PostMapping
    public ResponseEntity<Wallet> createWallet(@RequestBody Wallet walletRequest) {
        Wallet savedWallet = walletService.createWallet(walletRequest);
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
        return walletService.getWalletById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Deposits an amount into an existing wallet.
     *
     * @param id The UUID of the wallet.
     * @param request The deposit request containing the amount.
     * @return ResponseEntity containing the updated Wallet, or 400 Bad Request on error.
     */
    @PostMapping("/{id}/deposit")
    public ResponseEntity<Wallet> depositToWallet(@PathVariable UUID id, @RequestBody DepositRequest request) {
        try {
            Wallet updatedWallet = walletService.deposit(id, request.amount());
            return ResponseEntity.ok(updatedWallet);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}