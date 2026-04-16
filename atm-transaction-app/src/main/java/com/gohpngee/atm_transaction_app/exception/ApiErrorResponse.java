package com.gohpngee.atm_transaction_app.exception;

import java.time.Instant;

public record ApiErrorResponse(
        int status,
        String error,
        String message,
        Instant timestamp
) {}
