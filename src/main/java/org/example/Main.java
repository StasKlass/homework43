package org.example;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final String USERS_FILE = "users.txt";
    private static final String COUNTRIES_FILE = "countries.txt";

    public static void main(String[] args) {
        try {
            AuthenticationManager authManager = new AuthenticationManager(USERS_FILE);
            CountryManager countryManager = new CountryManager(COUNTRIES_FILE);

            Scanner scanner = new Scanner(System.in);

            System.out.println("Введите логин:");
            String username = scanner.nextLine();
            System.out.println("Введите пароль:");
            String password = scanner.nextLine();

            User user = authManager.authenticate(username, password);
            if (user == null) {
                System.out.println("Неверные логин или пароль.");
                return;
            }

            System.out.println("Добро пожаловать, " + user.getUsername() + "! Ваш уровень доступа: " + user.getRole());

            boolean running = true;
            while (running) {
                System.out.println("Выберите действие: ");
                System.out.println("1. Просмотреть страны");
                if (user.getRole().equals("admin")) {
                    System.out.println("2. Добавить страну");
                    System.out.println("3. Обновить страну");
                    System.out.println("4. Удалить страну");
                }
                System.out.println("5. Выйти");

                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        for (Country country : countryManager.getCountries()) {
                            System.out.println(country);
                        }
                        break;
                    case 2:
                        if (user.getRole().equals("admin")) {
                            System.out.println("Введите данные страны (название, столица, население, площадь):");
                            String[] countryData = scanner.nextLine().split(",\\s*");
                            if (countryData.length != 4) {
                                System.out.println("Неверный формат ввода. Пожалуйста, введите данные в формате: название, столица, население, площадь");
                                break;
                            }
                            try {
                                Country newCountry = new Country(countryData[0].trim(), countryData[1].trim(),
                                        Integer.parseInt(countryData[2].trim()),
                                        Integer.parseInt(countryData[3].replaceAll("\\D", "").trim()));
                                countryManager.addCountry(newCountry);
                                System.out.println("Страна добавлена.");
                            } catch (NumberFormatException e) {
                                System.out.println("Ошибка ввода числовых данных. Пожалуйста, повторите попытку.");
                            }
                        } else {
                            System.out.println("Недостаточно прав.");
                        }
                        break;
                    case 3:
                        if (user.getRole().equals("admin")) {
                            System.out.println("Введите название страны для обновления:");
                            String countryName = scanner.nextLine();
                            System.out.println("Введите новые данные страны (название, столица, население, площадь):");
                            String[] updatedData = scanner.nextLine().split(",\\s*");
                            if (updatedData.length != 4) {
                                System.out.println("Неверный формат ввода. Пожалуйста, введите данные в формате: название, столица, население, площадь");
                                break;
                            }
                            try {
                                Country updatedCountry = new Country(updatedData[0].trim(), updatedData[1].trim(),
                                        Integer.parseInt(updatedData[2].trim()),
                                        Integer.parseInt(updatedData[3].replaceAll("\\D", "").trim()));
                                countryManager.updateCountry(countryName, updatedCountry);
                                System.out.println("Данные страны обновлены.");
                            } catch (NumberFormatException e) {
                                System.out.println("Ошибка ввода числовых данных. Пожалуйста, повторите попытку.");
                            }
                        } else {
                            System.out.println("Недостаточно прав.");
                        }
                        break;
                    case 4:
                        if (user.getRole().equals("admin")) {
                            System.out.println("Введите название страны для удаления:");
                            String countryName = scanner.nextLine();
                            countryManager.deleteCountry(countryName);
                            System.out.println("Страна удалена.");
                        } else {
                            System.out.println("Недостаточно прав.");
                        }
                        break;
                    case 5:
                        running = false;
                        break;
                    default:
                        System.out.println("Некорректный выбор.");
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

