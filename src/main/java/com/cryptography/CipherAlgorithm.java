package com.cryptography;

public interface CipherAlgorithm {
    public String encrypt(String plainText);
    public String decrypt(String cipherText);
}
