package com.chemacoder.api_wallet.dto;

import java.math.BigDecimal;

/**
 * Data Transfer Object for withdraw requests.
 */
public record WithdrawRequest(BigDecimal amount) {
}