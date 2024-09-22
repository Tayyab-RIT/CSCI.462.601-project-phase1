package com.cryptography;

public class CeasarCipher implements CipherAlgorithm {
    private int key;

    public CeasarCipher(int key) {
        this.key = key;
    }

    @Override
    public String encrypt(String plainText) {
        plainText = plainText.toLowerCase();
        String cipherText = "";
        for (int i = 0; i < plainText.length(); i++) {
            int plainChar = plainText.charAt(i);
            int cipherChar;
            if (plainChar >= 'a' && plainChar <= 'z') {
                cipherChar = (plainChar - 'a' + this.key) % 26 + 'a';
            } else {
                cipherChar = plainChar;
            }
            cipherText += (char) cipherChar;
        }
        return cipherText;
    }

    @Override
    public String decrypt(String cipherText) {
        cipherText = cipherText.toLowerCase();
        String plainText = "";
        for (int i = 0; i < cipherText.length(); i++) {
            int cipherChar = cipherText.charAt(i);
            int plainChar;
            if (cipherChar >= 'a' && cipherChar <= 'z') {
                int shift = (cipherChar - 'a' - this.key) % 26;
                if (shift < 0) {
                    shift += 26;
                }
                plainChar = shift + 'a';
            } else {
                plainChar = cipherChar;
            }
            plainText += (char) plainChar;
        }
        return plainText;
    }



}
