package org.example.EFDb;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.persistence.*;
import java.io.IOException;
import java.util.List;

import static javafx.scene.input.DataFormat.URL;

public class EFDb extends Application {

    private static final EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("hibernate");
    static final ObservableList olFilmTitles = FXCollections.observableArrayList();
    private static Stage stg;

    public static void main(String[] args) {
        EntityManager entityManager = emFactory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Query query = entityManager.createNativeQuery("SELECT title FROM film");
            List<String> films = query.getResultList();
            for (String title : films){
                olFilmTitles.add(title);
            }
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        } finally {
            entityManager.close();
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stg = primaryStage;

        ComboBox comboBox = new ComboBox(olFilmTitles);
        VBox vbox = new VBox();

        TextField tfUserName = new TextField();
        TextField tfPassword = new TextField();
        Button bLogin = new Button();
        vbox.getChildren().addAll(tfUserName, tfPassword, bLogin);

        comboBox.setPromptText("Film titlar");
        TilePane r = new TilePane();
        TilePane tile_pane = new TilePane(vbox);
        Scene scene = new Scene(tile_pane, 200, 200);
        primaryStage.setTitle("EFDb");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
