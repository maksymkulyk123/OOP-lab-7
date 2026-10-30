package ua.opnu;

import java.util.Arrays;
import java.util.Objects;

public class Student {
    // name зберігає прізвище та ім'я: наприклад, "Бондар Олена".
    private final String name;
    private final String group;
    private final int[] marks;

    public Student(String name, String group, int[] marks) {
        this.name = Objects.requireNonNull(name);
        this.group = Objects.requireNonNull(group);
        this.marks = Objects.requireNonNull(marks).clone();
        for (int mark : this.marks) {
            if (mark < 0 || mark > 100) {
                throw new IllegalArgumentException("Оцінка повинна бути від 0 до 100");
            }
        }
    }

    public String getName() { return name; }
    public String getGroup() { return group; }
    public int[] getMarks() { return marks.clone(); }

    @Override
    public String toString() {
        return name + ", " + group + ", оцінки: " + Arrays.toString(marks);
    }
}
