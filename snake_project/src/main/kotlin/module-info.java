module com.example.snake_project {
        requires javafx.controls;
        requires javafx.fxml;
        requires kotlin.stdlib;

        opens com.example.snake_project to javafx.fxml;
        exports com.example.snake_project;
}
