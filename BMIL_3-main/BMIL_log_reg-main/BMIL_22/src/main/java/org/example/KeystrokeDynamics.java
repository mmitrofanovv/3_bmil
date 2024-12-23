package org.example;

import java.util.ArrayList;
import java.util.List;

public class KeystrokeDynamics {
    private final List<Long> keyPressTimestamps = new ArrayList<>();
    private final List<Long> keyReleaseTimestamps = new ArrayList<>();
    private final List<Long> intervals = new ArrayList<>();

    // Обработка события нажатия клавиши
    public void keyPress() {
        keyPressTimestamps.add(System.currentTimeMillis());
    }

    // Обработка события отпускания клавиши
    public void keyRelease() {
        long releaseTime = System.currentTimeMillis();
        keyReleaseTimestamps.add(releaseTime);

        // Вычисление интервала между текущим и предыдущим событием
        if (keyPressTimestamps.size() > 1) {
            long interval = releaseTime - keyPressTimestamps.get(keyPressTimestamps.size() - 2);
            intervals.add(interval);
        }
    }

    // Формирование матрицы межсимвольных интервалов
    public long[][] getIntervalMatrix(String password) {
        int n = password.length();
        long[][] matrix = new long[n][n];

        for (int i = 0; i < intervals.size(); i++) {
            for (int j = i + 1; j < intervals.size(); j++) {
                matrix[i][j] = intervals.get(j - i - 1);
            }
        }
        return matrix;
    }

    // Очистка данных
    public void clear() {
        keyPressTimestamps.clear();
        keyReleaseTimestamps.clear();
        intervals.clear();
    }
    public int calculateHammingDistance(List<Long> intervals1, List<Long> intervals2) {
        if (intervals1.size() != intervals2.size()) {
            throw new IllegalArgumentException("Размеры списков должны совпадать.");
        }
        int distance = 0;
        for (int i = 0; i < intervals1.size(); i++) {
            if (!intervals1.get(i).equals(intervals2.get(i))) {
                distance++;
            }
        }
        return distance;
    }

}
