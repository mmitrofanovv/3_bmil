package org.example;

import java.util.HashSet;
import java.util.Set;

public class PasswordValidator { //класс для проверки пароля пользователя при регистрации

    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBER = "1234567890";
    private static final String SPEC_SYMBOL = "!@#$%^&*()_+";
    private static final String DICTIONARY = UPPER + LOWER + NUMBER + SPEC_SYMBOL;

    // Проверка пароля на соответствие всем параметрам
    public static boolean validatePassword(String password) {
        if (password.length() < 8) { // Минимальная длина
            System.out.println("Ошибка: Пароль должен быть не менее 8 символов.");
            return false;
        }

        // Наборы для проверки уникальности
        Set<Character> uniqueChars = new HashSet<>();
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (!DICTIONARY.contains(String.valueOf(c))) { // Проверка на допустимые символы
                System.out.println("Ошибка: Пароль содержит недопустимые символы.");
                return false;
            }

            // Уникальность символов
            if (!uniqueChars.add(c)) {
                System.out.println("Ошибка: Пароль содержит повторяющиеся символы.");
                return false;
            }

            // Проверка групп символов
            if (UPPER.indexOf(c) >= 0) hasUpper = true;
            if (LOWER.indexOf(c) >= 0) hasLower = true;
            if (NUMBER.indexOf(c) >= 0) hasNumber = true;
            if (SPEC_SYMBOL.indexOf(c) >= 0) hasSpecial = true;
        }

        // Проверка наличия хотя бы одного символа из каждой группы
        if (!hasUpper || !hasLower || !hasNumber || !hasSpecial) {
            System.out.println("Ошибка: Пароль должен содержать хотя бы одну заглавную букву, строчную букву, цифру и специальный символ.");
            return false;
        }

        System.out.println("Пароль соответствует требованиям.");
        return true;
    }
}
