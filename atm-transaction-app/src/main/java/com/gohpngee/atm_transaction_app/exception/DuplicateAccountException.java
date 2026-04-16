package com.gohpngee.atm_transaction_app.exception;

public class DuplicateAccountException extends RuntimeException {
    public DuplicateAccountException(String message) {
        super(message);
    }
}
