package ua.opnu;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class Lab7Test {
    @Test void primeBoundariesAndLargeValues() {
        for (int number : new int[]{Integer.MIN_VALUE, -7, 0, 1, 4, 9, 25, 49, 2_147_395_600}) {
            assertFalse(Main.IS_PRIME.test(number), "not prime: " + number);
        }
        for (int number : new int[]{2, 3, 5, 17, 97, Integer.MAX_VALUE}) {
            assertTrue(Main.IS_PRIME.test(number), "prime: " + number);
        }
    }

    @Test void filtersPreserveOrderAndSourceIncludingEmptyResults() {
        int[] input = {11, 2, 4, 7, 2};
        assertArrayEquals(new int[]{11, 2, 7, 2}, ArrayOperations.filter(input, Main.IS_PRIME));
        assertArrayEquals(new int[]{11, 2, 4, 7, 2}, input);
        assertArrayEquals(new int[0], ArrayOperations.filter(input, n -> false));
        assertArrayEquals(new int[0], ArrayOperations.filter(new int[0], Main.IS_PRIME));
    }

    @Test void debtBoundaryAndStudentFiltering() {
        Student passing = new Student("A", "G", new int[]{60, 100});
        Student debt = new Student("B", "G", new int[]{100, 59});
        Student noMarks = new Student("C", "H", new int[]{});
        assertArrayEquals(new Student[]{passing, noMarks}, ArrayOperations.filter(
                new Student[]{passing, debt, noMarks}, Main.WITHOUT_DEBTS));
        assertArrayEquals(new Student[0], ArrayOperations.filter(new Student[0], Main.WITHOUT_DEBTS));
        assertArrayEquals(new Student[]{passing}, ArrayOperations.filter(
                new Student[]{passing, debt, noMarks}, Main.WITHOUT_DEBTS,
                student -> student.getGroup().equals("G")));
    }

    @Test void twoConditionsUseAndAndShortCircuit() {
        List<Integer> seen = new ArrayList<>();
        assertArrayEquals(new int[]{2, 4}, ArrayOperations.filter(new int[]{-2, 0, 2, 3, 4},
                n -> n > 0, n -> { seen.add(n); return n % 2 == 0; }));
        assertEquals(List.of(2, 3, 4), seen);
    }

    @Test void consumerReceivesOnlyRequestedStudentsInOrder() {
        List<String> names = new ArrayList<>();
        Student[] students = {new Student("Бондар Олена", "G", new int[]{60}),
                new Student("Коваль Андрій", "G", new int[]{90})};
        ArrayOperations.forEach(students, student -> names.add(student.getName()));
        assertEquals(List.of("Бондар Олена", "Коваль Андрій"), names);
        ArrayOperations.forEach(new Student[0], student -> fail("empty input"));
    }

    @Test void conditionalConsumerSkipsUnmatchedValuesAndEmptyInput() {
        List<Integer> squares = new ArrayList<>();
        ArrayOperations.forEachIf(new int[]{-2, 0, 2, 3, 4}, n -> n > 0 && n % 2 == 0,
                n -> squares.add(n * n));
        assertEquals(List.of(4, 16), squares);
        ArrayOperations.forEachIf(new int[0], n -> true, n -> fail("empty input"));
    }

    @Test void powersHaveExactIntBoundariesAndRejectOverflow() {
        assertArrayEquals(new int[]{1, 2, 4, 8, 16, 32, 64, 128, 256, 512},
                ArrayOperations.processArray(new int[]{0,1,2,3,4,5,6,7,8,9}, Main.POWER_OF_TWO));
        assertEquals(1_073_741_824, Main.POWER_OF_TWO.apply(30));
        assertThrows(IllegalArgumentException.class, () -> Main.POWER_OF_TWO.apply(-1));
        assertThrows(IllegalArgumentException.class, () -> Main.POWER_OF_TWO.apply(31));
        assertArrayEquals(new int[0], ArrayOperations.processArray(new int[0], Main.POWER_OF_TWO));
    }

    @Test void stringifyAllDigitsAndRejectInvalidNumbers() {
        assertArrayEquals(new String[]{"нуль", "один", "два", "три", "чотири", "п'ять",
                "шість", "сім", "вісім", "дев'ять"}, ArrayOperations.stringify(
                new int[]{0,1,2,3,4,5,6,7,8,9}, Main.DIGIT_TO_WORD));
        assertThrows(IllegalArgumentException.class, () -> Main.DIGIT_TO_WORD.apply(-1));
        assertThrows(IllegalArgumentException.class, () -> Main.DIGIT_TO_WORD.apply(10));
        assertArrayEquals(new String[0], ArrayOperations.stringify(new int[0], Main.DIGIT_TO_WORD));
    }

    @Test void studentMarksCannotBeChangedThroughArrayAliases() {
        int[] marks = {60};
        Student student = new Student("A", "G", marks);
        marks[0] = 1;
        student.getMarks()[0] = 1;
        assertArrayEquals(new int[]{60}, student.getMarks());
        assertThrows(IllegalArgumentException.class, () -> new Student("A", "G", new int[]{101}));
        assertThrows(IllegalArgumentException.class, () -> new Student("A", "G", new int[]{-1}));
    }

    @Test void completeDemoRunsAndContainsAllSevenSections() {
        PrintStream previous = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            Main.main(new String[0]);
        } finally {
            System.setOut(previous);
        }
        String text = output.toString(StandardCharsets.UTF_8);
        for (int i = 1; i <= 7; i++) { assertTrue(text.contains("\n" + i + ". ")); }
        assertTrue(text.contains("[2, 3, 5, 17]"));
        assertTrue(text.contains("[1, 2, 4, 8, 16, 32, 64, 128, 256, 512]"));
    }
}
