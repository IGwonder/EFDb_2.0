package org.example.EFDb;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
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
        PasswordField tfPassword = new PasswordField();
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
                createHomeScene(primaryStage);
            }
            tfUserName.clear();
            tfPassword.clear();
        });



        primaryStage.show();
    }

    private void createHomeScene(Stage primaryStage) {
        BorderPane borderPane2 = new BorderPane();
        HBox buttonBar = new HBox();
        borderPane2.setBottom(buttonBar);
        Button filmButton = new Button(buttonBar.toString());
        filmButton.setText("Filmer");
        filmButton.setLayoutX(250);
        filmButton.setLayoutY(220);
        Button actorButton = new Button(buttonBar.toString());
        actorButton.setText("Skådespelare");
        actorButton.setLayoutX(250);
        actorButton.setLayoutY(220);
        Button customerDbButton = new Button(buttonBar.toString());
        customerDbButton.setText("Kunder");
        customerDbButton.setLayoutX(250);
        customerDbButton.setLayoutY(220);
        Button addCustomerButton = new Button(buttonBar.toString());
        addCustomerButton.setText("Lägg Till Kund");
        addCustomerButton.setLayoutX(250);
        addCustomerButton.setLayoutY(220);
        Button rentalButton = new Button(buttonBar.toString());
        rentalButton.setText("Hyr Ut");
        rentalButton.setLayoutX(250);
        rentalButton.setLayoutY(220);
        Scene scene2 = new Scene(borderPane2,1280,720);
        buttonBar.getChildren().add(filmButton);
        buttonBar.getChildren().add(actorButton);
        buttonBar.getChildren().add(customerDbButton);
        buttonBar.getChildren().add(addCustomerButton);
        buttonBar.getChildren().add(rentalButton);
        primaryStage.setScene(scene2);
        primaryStage.show();


    }


    private Boolean checkLogInCredentials(TextField tfUserName, TextField tfPassword, Label lLogInAnswer) {
        if(!tfUserName.getText().toString().equals("EFDB1") && !tfPassword.getText().toString().equals("lol123")){
            lLogInAnswer.setText("Wrong username or password!");
            return false;
        } else return true;
    }

}
