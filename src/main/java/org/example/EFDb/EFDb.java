package org.example.EFDb;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.persistence.*;
import java.io.IOException;
import java.util.List;

public class EFDb extends Application {

    private static final EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("hibernate");
    private static final EntityManager entityManager = emFactory.createEntityManager();
    static final ObservableList olFilmTitles = FXCollections.observableArrayList();


    private static Stage stg;

    public static void main(String[] args) {

//        updateFilmTitles(emFactory,entityManager);
        launch(args);
    }

    public static void updateFilmTitles(EntityManagerFactory emFactory, EntityManager entityManager){
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
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stg = primaryStage;

        ComboBox comboBox = new ComboBox(olFilmTitles);
        comboBox.setPromptText("Film titlar");

        VBox vbox = new VBox();

        TextField tfUserName = new TextField();
        tfUserName.setPromptText("Username");
        TextField tfPassword = new TextField();
        tfPassword.setPromptText("Password");
        Button bLogin = new Button();
        bLogin.setText("Log in");
        Label lLogInAnswer = new Label();

        vbox.getChildren().addAll(tfUserName, tfPassword, bLogin, lLogInAnswer);

        BorderPane logIn = new BorderPane(vbox);

        Scene scene = new Scene(logIn, 200, 200);
        primaryStage.setTitle("EFDb");
        primaryStage.setScene(scene);

        // Button Action
        bLogin.setOnAction(event -> {
            if (checkLogInCredentials(tfUserName,tfPassword,lLogInAnswer)){
                VBox vBox2 = new VBox();
                BorderPane borderPane2 = new BorderPane();
                Scene scene2 = new Scene(borderPane2,200,200);
                primaryStage.setScene(scene2);
            }
            tfUserName.clear();
            tfPassword.clear();
        });



        primaryStage.show();
    }

    private Boolean checkLogInCredentials(TextField tfUserName, TextField tfPassword, Label lLogInAnswer) {
        if(!tfUserName.getText().toString().equals("EFDB1") && !tfPassword.getText().toString().equals("lol123")){
            lLogInAnswer.setText("Wrong username or password!");
            return false;
        } else return true;
    }

}
