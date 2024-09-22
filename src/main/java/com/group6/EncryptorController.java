package com.group6;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import com.cryptography.MonoCaesarCipher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EncryptorController {

    @FXML
    private TextField keyField;

    @FXML
    private TextArea plainTextArea;

    @FXML
    private TextArea logArea;

    @FXML
    private TextArea encryptedTextArea;

    @FXML
    private Button encryptButton;

    @FXML
    private Button saveToFileButton;

    // Handle loading key from a text file
    @FXML
    private void handleLoadKeyFromFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Key File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        fileChooser.setInitialDirectory(new File("./src/main/resources"));

        // Show open file dialog
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                // Read the key from the file and set it to the keyField
                String key = Files.readString(selectedFile.toPath()).trim();
                keyField.setText(key);
            } catch (IOException e) {
                logArea.appendText("Error reading key file: " + e.getMessage() + "\n");
            }
        }
    }

    // Handle loading plaintext from a text file
    @FXML
    private void handleLoadPlainTextFromFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Plaintext File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        fileChooser.setInitialDirectory(new File("./src/main/resources"));

        // Show open file dialog
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                // Read the plaintext from the file and set it to the plainTextField
                String plainText = Files.readString(selectedFile.toPath()).trim();
                plainTextArea.setText(plainText);
            } catch (IOException e) {
                logArea.appendText("Error reading plaintext file: " + e.getMessage() + "\n");
            }
        }
    }

    // Your encryption logic (connect to the MonoCaesarCipher class)
    @FXML
    private void handleEncryption() {
        String key = keyField.getText();
        String plainText = plainTextArea.getText();

        // Clear previous logs
        logArea.clear();

        // Initialize the MonoCaesarCipher class
        MonoCaesarCipher cipher;
        try {
            cipher = new MonoCaesarCipher(key);
        } catch (Exception e) {
            logArea.appendText("Error initializing cipher: " + e.getMessage() + "\n");
            return;
        }

        // Perform encryption and display the logs
        String cipherText = cipher.encrypt(plainText);
        for (String log : cipher.getLogs()) {
            logArea.appendText(log + "\n");
        }

        // Display the final encrypted text in the label
        encryptedTextArea.setText(cipherText);
    }

    @FXML
    private void handleSaveToFile() {
        if (encryptedTextArea.getText().isEmpty()) {
            logArea.appendText("No encrypted text to save.\n");
            return;
        }
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Encrypted Text");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            String filePath = fileChooser.showSaveDialog(null).getAbsolutePath(); // Get the file path as a string

            if (filePath != null && !filePath.isEmpty()) {
                try {
                    Path path = Paths.get(filePath); // Convert the file path string to a Path object
                    Files.writeString(path, encryptedTextArea.getText()); // Write the encrypted text to the file
                    logArea.appendText("Encrypted text saved to: " + filePath + "\n");
                } catch (IOException e) {
                    logArea.appendText("Error saving to file: " + e.getMessage() + "\n");
                }
            } else {
                logArea.appendText("File path is invalid or no file selected.\n");
            }
        } catch (Exception e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }

    }
}
