package com.suraj.bank.transferservice.exception;

public class DuplicateTransferException extends RuntimeException {

    public DuplicateTransferException() {
        super("Transfer with this idempotency key already exists");
    }
}