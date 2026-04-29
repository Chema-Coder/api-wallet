package com.chemacoder.api_wallet.dto;

import java.math.BigDecimal;

/**
 * Data Transfer Object for deposit requests.
 */
public record DepositRequest(BigDecimal amount) {
}