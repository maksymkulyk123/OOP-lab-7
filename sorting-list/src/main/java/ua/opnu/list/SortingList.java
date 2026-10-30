package ua.opnu.list;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SortingList extends Application {
    private ObservableList<Student> students;
    private boolean nameAscending = true;
    private boolean lastNameAscending = true;
    private boolean markAscending = true;

    @Override
    public void start(Stage stage) {
        students = FXCollections.observableArrayList(
                new Student("Борис", "Іванов", 75),
                new Student("Петро", "Петренко", 92),
                new Student("Сергій", "Сергієнко", 61),
                new Student("Григорій", "Сковорода", 88));
        ListView<Student> list = new ListView<>(students);
        list.setId("students");
        list.setPrefSize(620, 240);

        Button name = new Button("За ім'ям");
        Button lastName = new Button("За прізвищем");
        Button mark = new Button("За оцінкою");
        name.setId("by-name");
        lastName.setId("by-last-name");
        mark.setId("by-mark");
        // І обробники подій, і компаратори реалізовано лямбда-виразами.
        name.setOnAction(event -> {
            students.sort(StudentSorts.byName(nameAscending));
            nameAscending = !nameAscending;
        });
        lastName.setOnAction(event -> {
            students.sort(StudentSorts.byLastName(lastNameAscending));
            lastNameAscending = !lastNameAscending;
        });
        mark.setOnAction(event -> {
            students.sort(StudentSorts.byMark(markAscending));
            markAscending = !markAscending;
        });
        HBox buttons = new HBox(5, name, lastName, mark);
        buttons.setAlignment(Pos.CENTER);
        for (Button button : new Button[]{name, lastName, mark}) {
            HBox.setHgrow(button, Priority.ALWAYS);
            button.setMaxWidth(Double.MAX_VALUE);
        }
        VBox layout = new VBox(8, new Label("Демонстраційні дані. Повторне натискання змінює напрям."),
                list, buttons);
        layout.setPadding(new Insets(10));
        stage.setTitle("ЛР7 — SortingList: лямбда-вирази");
        stage.setScene(new Scene(layout));
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) { launch(args); }
}
