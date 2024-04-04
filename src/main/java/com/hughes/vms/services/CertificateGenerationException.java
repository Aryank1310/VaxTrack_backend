package com.hughes.vms.services;

public class CertificateGenerationException extends RuntimeException {
    public CertificateGenerationException(String message) {
        super(message);
    }

    public CertificateGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
