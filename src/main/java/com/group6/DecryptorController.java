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

public class DecryptorController {

    @FXML
    private TextField keyField;

    @FXML
    private TextArea cipherTextArea;

    @FXML
    private TextArea logArea;

    @FXML
    private TextArea decryptedTextArea;

    @FXML
    private Button decryptButton;

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

    // Handle loading ciphertext from a text file
    @FXML
    private void handleLoadCipherTextFromFile() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Ciphertext File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            fileChooser.setInitialDirectory(new File("./src/main/resources"));

            // Show open file dialog
            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                try {
                    // Read the ciphertext from the file and set it to the cipherTextField
                    String cipherText = Files.readString(selectedFile.toPath()).trim();
                    cipherTextArea.setText(cipherText);
                } catch (IOException e) {
                    logArea.appendText("Error reading ciphertext file: " + e.getMessage() + "\n");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    // Your encryption logic (connect to the MonoCaesarCipher class)
    @FXML
    private void handleDecryption() {
        String key = keyField.getText();
        String cipherText = cipherTextArea.getText();

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
        String plainText = cipher.decrypt(cipherText);
        for (String log : cipher.getLogs()) {
            logArea.appendText(log + "\n");
        }

        // Display the final encrypted text in the label
        decryptedTextArea.setText(plainText);
    }

    @FXML
    private void handleSaveToFile() {
        if (decryptedTextArea.getText().isEmpty()) {
            logArea.appendText("No decrypted text to save.\n");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Decrypted Text");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        String filePath = fileChooser.showSaveDialog(null).getAbsolutePath(); // Get the file path as a string

        if (filePath != null && !filePath.isEmpty()) {
            try {
                Path path = Paths.get(filePath); // Convert the file path string to a Path object
                Files.writeString(path, decryptedTextArea.getText()); // Write the encrypted text to the file
                logArea.appendText("Encrypted text saved to: " + filePath + "\n");
            } catch (IOException e) {
                logArea.appendText("Error saving to file: " + e.getMessage() + "\n");
            }
        } else {
            logArea.appendText("File path is invalid or no file selected.\n");
        }
    }
}
