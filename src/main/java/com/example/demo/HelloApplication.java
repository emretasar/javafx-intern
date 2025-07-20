package com.example.demo;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {

    private static final int ROWS = 120;  // Dynamic row count
    private static final int COLS = 60;  // Dynamic column count

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // 1. Create MenuBar
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem newItem = new MenuItem("New");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> stage.close());
        fileMenu.getItems().addAll(newItem, exitItem);
        menuBar.getMenus().add(fileMenu);

        // 2. Create TableView (with dynamic columns and rows)
        TableView<List<StringProperty>> tableView = new TableView<>();
        tableView.setEditable(true);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Create dynamic columns
        for (int col = 0; col < COLS; col++) {
            final int columnIndex = col;
            TableColumn<List<StringProperty>, String> column = new TableColumn<>("Column " + (col + 1));
            column.setCellValueFactory(cellData -> cellData.getValue().get(columnIndex));
            column.setMinWidth(100);  // Adjust column width
            tableView.getColumns().add(column);
        }

        // Create dynamic rows
        for (int row = 0; row < ROWS; row++) {
            List<StringProperty> rowData = new ArrayList<>();
            for (int col = 0; col < COLS; col++) {
                rowData.add(new SimpleStringProperty("R" + (row + 1) + "C" + (col + 1)));
            }
            tableView.getItems().add(rowData);
        }

        // 3. Create ScrollPane for the TableView
        ScrollPane scrollPane = new ScrollPane(tableView);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // 4. Create a BorderPane to hold the MenuBar and ScrollPane
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(scrollPane);

        // 5. Set Scene and Stage properties
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Responsive Table with ScrollBars");
        stage.setScene(scene);
        stage.show();
    }
}
