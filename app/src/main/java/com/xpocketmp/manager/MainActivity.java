package com.xpocketmp.manager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView consoleOutput;
    private EditText consoleInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startServerButton = findViewById(R.id.start_server_button);
        Button openFileManagerButton = findViewById(R.id.open_file_manager);
        consoleOutput = findViewById(R.id.console_output);
        consoleInput = findViewById(R.id.console_input);

        // Start Server
        startServerButton.setOnClickListener(v -> startXPocketMP());

        // Open File Manager
        openFileManagerButton.setOnClickListener(v -> openFileManager());

        // Execute Console Command
        consoleInput.setOnEditorActionListener((v, actionId, event) -> {
            String command = consoleInput.getText().toString();
            executeCommand(command);
            consoleInput.setText("");
            return true;
        });
    }

    private void startXPocketMP() {
        try {
            File phpBinary = new File(getFilesDir(), "php/php");
            File serverDir = new File(getFilesDir(), "server");

            // Run PHP Command
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command(phpBinary.getAbsolutePath(), "start.php");
            processBuilder.directory(serverDir);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            // Display Output
            new Thread(() -> {
                try {
                    int read;
                    byte[] buffer = new byte[1024];
                    while ((read = process.getInputStream().read(buffer)) != -1) {
                        runOnUiThread(() -> consoleOutput.append(new String(buffer, 0, read)));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            consoleOutput.append("\nError: " + e.getMessage());
        }
    }

    private void executeCommand(String command) {
        consoleOutput.append("\n$ " + command);
        // Logic for executing command on the server can be added here.
    }

    private void openFileManager() {
        consoleOutput.append("\nOpening File Manager...");
        // Logic for launching a file manager
    }
}
