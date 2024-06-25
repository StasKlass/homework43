package org.example;

import org.example.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AuthenticationManager {
    private Map<String, User> users = new HashMap<>();

    public AuthenticationManager(String filePath) throws IOException {
        loadUsers(filePath);
    }

    private void loadUsers(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filePath))))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    users.put(parts[0], new User(parts[0], parts[1], parts[2]));
                }
            }
        }
    }

    public User authenticate(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
