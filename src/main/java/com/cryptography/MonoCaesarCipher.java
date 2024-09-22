package com.cryptography;

import java.util.HashMap;
import com.logger.Logger;

import java.util.ArrayList;

public class MonoCaesarCipher implements CipherAlgorithm {
    private String key;
    private HashMap<Character, Character> forwardKeyMap; // Map from plain text to cipher text using the key
    private HashMap<Character, Character> reverseKeyMap; // Map from cipher text to plain text using the key
    private Logger logger;

    public MonoCaesarCipher(String key) {
        if (key.length() != 26) {
            throw new IllegalArgumentException("Key must be 26 characters long");
        }
        this.key = key;
        this.forwardKeyMap = generateForwardKeyMap();
        this.reverseKeyMap = generateReverseKeyMap();
        this.logger = new Logger();
    }

    public ArrayList<String> getLogs() {
        return this.logger.getLogs();
    }

    public MonoCaesarCipher(String key, Logger logger) {
        if (key.length() != 26) {
            throw new IllegalArgumentException("Key must be 26 characters long");
        }
        this.key = key;
        this.forwardKeyMap = generateForwardKeyMap();
        this.reverseKeyMap = generateReverseKeyMap();
        this.logger = logger;
    }

    @Override
    public String encrypt(String plainText) {
        plainText = plainText.toLowerCase();
        this.logger.log("Recieved plain text: " + plainText);
        String cipherText = "";
        int counter = 0;
        int shift = 0;
        for (int i = 0; i < plainText.length(); i++) {
            int plainChar = plainText.charAt(i);
            int cipherChar;
            if (plainChar >= 'a' && plainChar <= 'z') {
                this.logger.log("Got character '" + (char) plainChar + "' at position: " + counter);
                if (counter % 6 == 0) {
                    this.logger.log("Using key map substitution for character: " + (char) plainChar);
                    cipherChar = this.forwardKeyMap.get((char) plainChar);
                    this.logger.log("Got cipher character: " + (char) cipherChar);
                    shift = plainChar - 'a';
                    this.logger.log("Updated shift value for next 5 characters to: " + shift);
                } else {
                    this.logger.log("Using shift cipher with shift value: " + shift);
                    cipherChar = new CeasarCipher(shift).encrypt(Character.toString((char) plainChar)).charAt(0);
                    this.logger.log("Got cipher character: " + (char) cipherChar);
                }
                counter++;
            } else {
                this.logger.log("Got character '" + (char) plainChar + "', which is not a letter between a and z");
                cipherChar = plainChar;
                this.logger.log("Not replacing " + (char) plainChar);
            }
            cipherText += (char) cipherChar;
            this.logger.log("Replacing " + (char) plainChar + " with " + (char) cipherChar);
            this.logger.log("Current cipher text: " + cipherText);
        }
        this.logger.log("Done encrypting. Cypher text generated: " + cipherText);
        return cipherText;
    }

    @Override
    public String decrypt(String cipherText) {
        cipherText = cipherText.toLowerCase();
        String plainText = "";
        int counter = 0;
        int shift = 0;
        for (int i = 0; i < cipherText.length(); i++) {
            int cipherChar = cipherText.charAt(i);
            int plainChar;
            if (cipherChar >= 'a' && cipherChar <= 'z') {
                this.logger.log("Got character '" + (char) cipherChar + "' at position: " + counter);
                if (counter % 6 == 0) {
                    this.logger.log("Using reverse key map substitution for character: " + (char) cipherChar);
                    plainChar = this.reverseKeyMap.get((char) cipherChar);
                    this.logger.log("Got plain character: " + (char) plainChar);
                    shift = plainChar - 'a';
                    this.logger.log("Updated shift value for next 5 characters to: " + shift);
                } else {
                    this.logger.log("Using reverse shift cipher with shift value: " + shift);
                    plainChar = new CeasarCipher(shift).decrypt(Character.toString((char) cipherChar)).charAt(0);
                    this.logger.log("Got plain character: " + (char) cipherChar);
                }
                counter++;
            } else {
                this.logger.log("Got character '" + (char) cipherChar + "', which is not a letter between a and z");
                plainChar = cipherChar;
                this.logger.log("Not replacing " + (char) plainChar);
            }
            plainText += (char) plainChar;
            this.logger.log("Replacing " + (char) cipherChar + " with " + (char) plainChar);
            this.logger.log("Current plain text: " + plainText);
        }
        return plainText;
    }

    private HashMap<Character, Character> generateForwardKeyMap() {
        HashMap<Character, Character> keyMap = new HashMap<>();
        for (int i = 0; i < 26; i++) {
            keyMap.put((char) ('a' + i), this.key.charAt(i));
        }
        return keyMap;
    }

    private HashMap<Character, Character> generateReverseKeyMap() {
        HashMap<Character, Character> keyMap = new HashMap<>();
        for (int i = 0; i < 26; i++) {
            keyMap.put(this.key.charAt(i), (char) ('a' + i));
        }
        return keyMap;
    }

}
