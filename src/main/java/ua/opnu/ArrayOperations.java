package ua.opnu;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class ArrayOperations {
    public static int[] filter(int[] input, Predicate<Integer> condition) {
        int[] result = new int[input.length];
        int count = 0;
        for (int value : input) {
            if (condition.test(value)) {
                result[count++] = value;
            }
        }
        return Arrays.copyOf(result, count);
    }

    public static Student[] filter(Student[] input, Predicate<Student> condition) {
        Student[] result = new Student[input.length];
        int count = 0;
        for (Student student : input) {
            if (condition.test(student)) {
                result[count++] = student;
            }
        }
        return Arrays.copyOf(result, count);
    }

    public static int[] filter(int[] input, Predicate<Integer> first,
                               Predicate<Integer> second) {
        return filter(input, value -> first.test(value) && second.test(value));
    }

    public static Student[] filter(Student[] input, Predicate<Student> first,
                                   Predicate<Student> second) {
        return filter(input, student -> first.test(student) && second.test(student));
    }

    public static void forEach(Student[] input, Consumer<Student> action) {
        for (Student student : input) {
            action.accept(student);
        }
    }

    public static void forEachIf(int[] input, Predicate<Integer> condition,
                                  Consumer<Integer> action) {
        for (int value : input) {
            if (condition.test(value)) {
                action.accept(value);
            }
        }
    }

    public static int[] processArray(int[] input, Function<Integer, Integer> function) {
        int[] result = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            result[i] = function.apply(input[i]);
        }
        return result;
    }

    public static String[] stringify(int[] input, Function<Integer, String> function) {
        String[] result = new String[input.length];
        for (int i = 0; i < input.length; i++) {
            result[i] = function.apply(input[i]);
        }
        return result;
    }
}
