package naumen.course;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Заполнить массив (тип элементов “int”) случайными числами.
 * Найти сумму положительных элементов массива
 */
public class Task1V5 {
    /**
     * Генератор псевдослучайных чисел
     */
    private static final Random random = new Random();

    /**
     * Решает задачу
     */
    private int solve(int arraySize) {
        int[] array = new int[arraySize];
        fillArrayWithRandomNumbers(array);
        System.out.println(Arrays.toString(array));
        return getSumOfPositiveElements(array);
    }

    /**
     * Возвращает сумму положительных элементов в массиве
     */
    private int getSumOfPositiveElements(int[] array) {
        return Arrays.stream(array).filter(i -> i > 0).sum();
    }

    /**
     * Заполняет массив случайными числами в диапазоне от -100 до 100
     */
    private void fillArrayWithRandomNumbers(int[] array) {
        for(int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(-100, 101);
        }
    }

    public static void main(String[] args) {
        System.out.print("array size: ");
        System.out.println(new Task1V5().solve(new Scanner(System.in).nextInt()));
    }
}
