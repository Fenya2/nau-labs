package naumen.course;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 * Заполнить список (тип “ArrayList<Double>”) случайным числами и
 * отсортировать его, используя алгоритм быстрой сортировки
 */
public class Task2V2 {
    /**
     * Генератор псевдослучайных чисел
     */
    private static final Random random = new Random();

    /**
     * Решает задачу
     */
    private void solve(int arraySize) {
        ArrayList<Double> list = new ArrayList<>(Collections.nCopies(arraySize, 0.0));
        fillArrayListWithRandomNumbers(list);
        System.out.println(list);
        quickSort(list, 0, list.size() - 1);
        System.out.println(list);
    }

    /**
     * Заполняет список случайными числами в диапазоне [-1000.0, 1000.0)
     */
    private void fillArrayListWithRandomNumbers(ArrayList<Double> list) {
        list.replaceAll(ignored -> random.nextDouble(-1000.0, 1000.0));
    }

    /**
     * Сортирует указанный список с использованием алгоритма быстрой сортировки.
     *
     * @param list  список
     * @param left  начальный индекс части списка, которую нужно отсортировать
     * @param right конечный индекс части списка, которую нужно отсортировать
     */
    private void quickSort(ArrayList<Double> list, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(list, left, right);
            quickSort(list, left, pivotIndex - 1);
            quickSort(list, pivotIndex, right);
        }
    }

    /**
     * Делит указанную часть списка вокруг опорного элемента.
     * Элементы, меньшие или равные опорному, перемещаются влево от опорного,
     * элементы, большие опорного, перемещаются вправо от опорного.
     *
     * @param list список
     * @param low  начальный индекс части списка, которую нужно разделить
     * @param high конечный индекс части списка, которую нужно разделить
     * @return индекс опорного элемента после разделения
     */
    private int partition(ArrayList<Double> list, int low, int high) {
        Double pivot = list.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (list.get(j) <= pivot) {
                i++;
                Collections.swap(list, i, j);
            }
        }
        Collections.swap(list, i + 1, high);
        return i + 1;
    }

    public static void main(String[] args) {
        System.out.print("array size: ");
        new Task2V2().solve(new Scanner(System.in).nextInt());
    }
}
