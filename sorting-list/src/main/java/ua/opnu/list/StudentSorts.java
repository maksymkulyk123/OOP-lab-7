package ua.opnu.list;

import java.util.Comparator;

public class StudentSorts {
    public static Comparator<Student> byName(boolean ascending) {
        return (first, second) -> ascending
                ? first.getName().compareTo(second.getName())
                : second.getName().compareTo(first.getName());
    }

    public static Comparator<Student> byLastName(boolean ascending) {
        return (first, second) -> ascending
                ? first.getLastName().compareTo(second.getLastName())
                : second.getLastName().compareTo(first.getLastName());
    }

    public static Comparator<Student> byMark(boolean ascending) {
        return (first, second) -> ascending
                ? Double.compare(first.getAvgMark(), second.getAvgMark())
                : Double.compare(second.getAvgMark(), first.getAvgMark());
    }
}
