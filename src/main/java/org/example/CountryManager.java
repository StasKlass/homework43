package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CountryManager {
    private List<Country> countries = new ArrayList<>();

    public CountryManager(String filePath) throws IOException {
        loadCountries(filePath);
    }

    private void loadCountries(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filePath))))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 4) {
                    Country country = new Country(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
                    countries.add(country);
                }
            }
        }
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void addCountry(Country country) throws IOException {
        countries.add(country);
        saveCountries();
    }

    public void updateCountry(String name, Country updatedCountry) throws IOException {
        for (int i = 0; i < countries.size(); i++) {
            if (countries.get(i).getName().equalsIgnoreCase(name)) {
                countries.set(i, updatedCountry);
                saveCountries();
                return;
            }
        }
    }

    public void deleteCountry(String name) throws IOException {
        countries.removeIf(country -> country.getName().equalsIgnoreCase(name));
        saveCountries();
    }

    private void saveCountries() throws IOException {
        // Implement saving functionality if needed
    }
}
