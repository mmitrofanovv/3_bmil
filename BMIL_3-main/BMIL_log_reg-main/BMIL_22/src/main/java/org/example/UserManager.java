package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserManager {
    private final UserRepository userRepository = new UserRepository();
    private final Scanner scanner = new Scanner(System.in);
    private static final long MAX_ALLOWED_DIFFERENCE = 50000000L; // 50 мс

    // Регистрация пользователя с записью интервалов нажатий клавиш
    public void register() {
        System.out.print("Введите логин: ");
        String username = scanner.nextLine();
        if (userRepository.isUserExists(username)) {
            System.out.println("Ошибка: Логин уже существует. Попробуйте другой.");
            return;
        }

        System.out.println("Введите пароль (закончите ввод нажатием Enter):");
        List<Long> typingIntervals = collectTypingIntervals();
        if (typingIntervals == null) return; // Ошибка ввода

        System.out.print("Подтвердите пароль: ");
        String password = scanner.nextLine();

        if (userRepository.registerUserWithIntervals(username, password, typingIntervals)) {
            System.out.println("Регистрация успешна.");
        } else {
            System.out.println("Ошибка при регистрации.");
        }
    }

    // Вход пользователя и проверка интервалов нажатий клавиш
    public void login() {
        System.out.print("Введите логин: ");
        String username = scanner.nextLine();

        System.out.println("Введите пароль (закончите ввод нажатием Enter):");
        List<Long> typingIntervals = collectTypingIntervals();
        if (typingIntervals == null) return; // Ошибка ввода

        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        if (userRepository.authenticate(username, password)) {
            String storedIntervals = userRepository.getUserIntervals(username);
            if (storedIntervals != null) {
                List<Long> registeredIntervals = userRepository.stringToIntervals(storedIntervals);
                if (compareIntervals(registeredIntervals, typingIntervals)) {
                    System.out.println("Аутентификация успешна. Биометрия совпадает.");
                } else {
                    System.out.println("Аутентификация успешна, но биометрия не совпадает.");
                }
            } else {
                System.out.println("Ошибка: Данные биометрии отсутствуют.");
            }
        } else {
            System.out.println("Ошибка: Неправильный логин или пароль.");
        }
    }

    // Сравнение интервалов нажатий клавиш
    private boolean compareIntervals(List<Long> registeredIntervals, List<Long> inputIntervals) {
        if (registeredIntervals.size() != inputIntervals.size()) {
            return false;
        }
        for (int i = 0; i < registeredIntervals.size(); i++) {
            long difference = Math.abs(registeredIntervals.get(i) - inputIntervals.get(i));
            if (difference > MAX_ALLOWED_DIFFERENCE) {
                return false;
            }
        }
        return true;
    }
    public boolean compareIntervals1(List<Long> storedIntervals, List<Long> inputIntervals) {
        int maxErrors = 2; // Допустимое количество ошибок
        int errors = 0;    // Счётчик ошибок

        for (int i = 0; i < storedIntervals.size(); i++) {
            long stored = storedIntervals.get(i);
            long input = inputIntervals.get(i);

            if (Math.abs(stored - input) > 50) { // Порог отклонения — 50 мс
                errors++; // Если интервал отличается более чем на 50 мс, это ошибка
                if (errors > maxErrors) {
                    return false; // Слишком много ошибок
                }
            }
        }

        return true; // Если ошибок меньше или равно maxErrors, биометрия считается совпадающей
    }

    // Сбор интервалов между нажатиями клавиш
    private List<Long> collectTypingIntervals() {
        List<Long> typingIntervals = new ArrayList<>();
        long lastPressTime = System.nanoTime();
        StringBuilder passwordBuilder = new StringBuilder();

        try {
            while (true) {
                if (System.in.available() > 0) {
                    int key = System.in.read();
                    if (key == '\n') break;

                    long currentPressTime = System.nanoTime();
                    if (passwordBuilder.length() > 0) {
                        typingIntervals.add(currentPressTime - lastPressTime);
                    }
                    lastPressTime = currentPressTime;
                    passwordBuilder.append((char) key);
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка ввода: " + e.getMessage());
            return null;
        }

        if (passwordBuilder.length() == 0) {
            System.out.println("Ошибка: Пароль не может быть пустым.");
            return null;
        }
        return typingIntervals;
    }

    // Запуск приложения
    public void start() {
        while (true) {
            System.out.println("\nСистема управления пользователями");
            System.out.println("1. Регистрация");
            System.out.println("2. Вход");
            System.out.println("3. Выход");
            System.out.print("Введите ваш выбор: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка ввода

            switch (choice) {
                case 1 -> register();
                case 2 -> login();
                case 3 -> {
                    System.out.println("Выход...");
                    return;
                }
                default -> System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }
}
