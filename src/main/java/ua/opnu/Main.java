package ua.opnu;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Main {
    public static final Predicate<Integer> IS_PRIME = number -> {
        if (number < 2) {
            return false;
        }
        // number / divisor запобігає переповненню divisor * divisor.
        for (int divisor = 2; divisor <= number / divisor; divisor++) {
            if (number % divisor == 0) {
                return false;
            }
        }
        return true;
    };

    public static final Predicate<Student> WITHOUT_DEBTS = student -> {
        for (int mark : student.getMarks()) {
            if (mark < 60) {
                return false;
            }
        }
        return true;
    };

    public static final Function<Integer, Integer> POWER_OF_TWO = exponent -> {
        // 2^31 вже не вміщується у додатний int; від'ємні степені не цілі.
        if (exponent < 0 || exponent > 30) {
            throw new IllegalArgumentException("Для int показник має бути від 0 до 30");
        }
        return 1 << exponent;
    };

    public static final Function<Integer, String> DIGIT_TO_WORD = digit -> {
        String[] words = {"нуль", "один", "два", "три", "чотири",
                "п'ять", "шість", "сім", "вісім", "дев'ять"};
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("Потрібна цифра від 0 до 9");
        }
        return words[digit];
    };

    public static void main(String[] args) {
        System.out.println("Лабораторна робота №7 — Кулик Максим Володимирович, АІ-254");
        System.out.println("Усі студенти й оцінки нижче — демонстраційні дані.");
        int[] numbers = {-3, 0, 1, 2, 3, 4, 5, 9, 17, 25};
        System.out.println("\n1. Прості числа: "
                + Arrays.toString(ArrayOperations.filter(numbers, IS_PRIME)));

        Student[] students = {
                new Student("Бондар Олена", "АІ-254", new int[]{60, 85, 100}),
                new Student("Коваль Андрій", "АІ-254", new int[]{90, 59, 80}),
                new Student("Мельник Ірина", "АІ-253", new int[]{75, 80, 95}),
                new Student("Шевченко Тарас", "АІ-254", new int[]{45, 50, 85})
        };
        System.out.println("\n2. Студенти без заборгованостей:");
        ArrayOperations.forEach(ArrayOperations.filter(students, WITHOUT_DEBTS),
                student -> System.out.println(student));

        System.out.println("\n3. Два предикати: немає боргів І група АІ-254:");
        Predicate<Student> fromGroup = student -> student.getGroup().equals("АІ-254");
        ArrayOperations.forEach(ArrayOperations.filter(students, WITHOUT_DEBTS, fromGroup),
                student -> System.out.println(student));

        System.out.println("\n4. Consumer і forEach — прізвище та ім'я:");
        Consumer<Student> printName = student -> System.out.println(student.getName());
        ArrayOperations.forEach(students, printName);

        System.out.println("\n5. Дія лише для додатних парних чисел:");
        Predicate<Integer> positiveEven = value -> value > 0 && value % 2 == 0;
        Consumer<Integer> printSquare = value -> System.out.println(
                value + "² = " + (long) value * value);
        ArrayOperations.forEachIf(numbers, positiveEven, printSquare);

        int[] tenNumbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println("\n6. Степені двійки для n = " + Arrays.toString(tenNumbers));
        System.out.println(Arrays.toString(ArrayOperations.processArray(tenNumbers, POWER_OF_TWO)));
        System.out.println("\n7. Цифри словами:");
        System.out.println(Arrays.toString(ArrayOperations.stringify(tenNumbers, DIGIT_TO_WORD)));
    }
}
