package org.example.EFDb;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.EFDb.Entities.ActorEntity;
import org.example.EFDb.Entities.CustomerEntity;

import javax.persistence.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EFDb extends Application {

    private static final EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("hibernate");
    private static final EntityManager entityManager = emFactory.createEntityManager();
    static final ObservableList olFilmTitles = FXCollections.observableArrayList();
    static final ObservableList olActorNames = FXCollections.observableArrayList();
    static final ObservableList olActors = FXCollections.observableArrayList();
    static final ObservableList olCustomer = FXCollections.observableArrayList();

    private static Stage stg;

    public static void main(String[] args) {

       connectToDatabase(entityManager);
      // updateActorNames(entityManager);

        launch(args);
    }

    public static void updateFilmTitles(EntityManager entityManager){
        Query query = entityManager.createNativeQuery("SELECT title FROM film");
        List<String> films = query.getResultList();
        for (String title : films){
            olFilmTitles.add(title);
        }
    }

    public static void updateActorNames(EntityManager entityManager){

        Query query = entityManager.createNativeQuery("SELECT first_name FROM actor");
        Query query2 = entityManager.createNativeQuery("SELECT last_name FROM actor");

        List<String> actorFirstname = query.getResultList();
        List<String> actorSurname = query2.getResultList();
        List<String> actorFullName = new ArrayList<>();
        for (int i = 0; i < actorFirstname.size(); i++){
            actorFullName.add(actorFirstname.get(i) + " " + actorSurname.get(i));
        }
        for (String name : actorFullName){
            olActorNames.add(name);
        }
    }

    public static void updateActors(EntityManager entityManager){
        Query actorIDQuery = entityManager.createNativeQuery("SELECT actor_id FROM actor");
        Query actorFirstNameQuery = entityManager.createNativeQuery("SELECT first_name FROM actor");
        Query actorLastNameQuery = entityManager.createNativeQuery("SELECT last_name FROM actor");
        Query actorLastUpdateQuery = entityManager.createNativeQuery("SELECT last_update FROM actor");

        List<Short> actorIDList = actorIDQuery.getResultList();
        List<String> actorFirstNameList = actorFirstNameQuery.getResultList();
        List<String> actorLastNameList = actorLastNameQuery.getResultList();
        List<Timestamp> actorLastUpdateList = actorLastUpdateQuery.getResultList();

        for(int i = 0; i < actorIDList.size(); i++){
            Short actorID = actorIDList.get(i);
            String firstName = actorFirstNameList.get(i);
            String lastName = actorLastNameList.get(i);
            Timestamp lastUpdate = actorLastUpdateList.get(i);

            ActorEntity actor = new ActorEntity(actorID, firstName, lastName, lastUpdate);
            olActors.add(actor);
        }

    }

    public static void updateCustomerList(EntityManager entityManager){
        Query customerIDQuery = entityManager.createNativeQuery("SELECT customer_id FROM customer");
        Query storeIDQuery = entityManager.createNativeQuery("SELECT store_id FROM customer");
        Query customerFirstNameQuery = entityManager.createNativeQuery("SELECT first_name FROM customer");
        Query customerLastNameQuery = entityManager.createNativeQuery("SELECT last_name FROM customer");
        Query emailQuery = entityManager.createNativeQuery("SELECT email FROM customer");
        Query addressIDQuery = entityManager.createNativeQuery("SELECT address_id FROM customer");
        Query activeQuery = entityManager.createNativeQuery("SELECT active FROM customer");
        Query createDateQuery = entityManager.createNativeQuery("SELECT create_date FROM customer");
        Query lastUpdateQuery = entityManager.createNativeQuery("SELECT last_update FROM customer");

        List<Short> customerIDList = customerIDQuery.getResultList();
        List<Byte> storeIDList = storeIDQuery.getResultList();
        List<String> customerFirstNameList = customerFirstNameQuery.getResultList();
        List<String> customerLastNameList = customerLastNameQuery.getResultList();
        List<String> emailList = emailQuery.getResultList();
        List<Short> addressIDList = addressIDQuery.getResultList();
        List<Boolean> activeList = activeQuery.getResultList();
        List<Timestamp> createDateList = createDateQuery.getResultList();
        List<Timestamp> lastUpdateList = lastUpdateQuery.getResultList();

        for(int i = 0; i < customerIDList.size(); i++){
            Short customerID = customerIDList.get(i);
            Byte storeID = storeIDList.get(i);
            String customerFirstName = (String) customerFirstNameList.get(i);
            String customerLastName = (String) customerLastNameList.get(i);
            String email = (String)emailList.get(i);
            Short addressID = addressIDList.get(i);
            Boolean active = activeList.get(i);
            Timestamp dateList = (Timestamp)createDateList.get(i);
            Timestamp lastUpdate = (Timestamp)lastUpdateList.get(i);

            CustomerEntity customer = new CustomerEntity(customerID, storeID, customerFirstName, customerLastName, email, addressID, active, dateList, lastUpdate);
            olCustomer.add(customer);
        }

    }

    public static void connectToDatabase(EntityManager entityManager){
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            updateFilmTitles(entityManager);
            updateActors(entityManager);
            updateActorNames(entityManager);
            updateCustomerList(entityManager);
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
        BorderPane homeBorderPane = new BorderPane();
        HBox buttonBar = new HBox();
        homeBorderPane.setBottom(buttonBar);

        Button filmButton = new Button(buttonBar.toString());
        filmButton.setText("Filmer");
        filmButton.setOnAction(event -> {
            createFilmPage(primaryStage);
        });
        filmButton.setLayoutX(250);
        filmButton.setLayoutY(220);

        Button actorButton = new Button(buttonBar.toString());
        actorButton.setText("Skådespelare");
        actorButton.setOnAction(event -> {
            createActorPage(primaryStage);
        });
        actorButton.setLayoutX(250);
        actorButton.setLayoutY(220);


        Button customerDbButton = new Button(buttonBar.toString());
        customerDbButton.setText("Kunder");
        customerDbButton.setOnAction(event -> {
            createCustomerDbPage(primaryStage);
        });
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

        Scene scene2 = new Scene(homeBorderPane,1280,720);
        buttonBar.getChildren().add(filmButton);
        buttonBar.getChildren().add(actorButton);
        buttonBar.getChildren().add(customerDbButton);
        buttonBar.getChildren().add(addCustomerButton);
        buttonBar.getChildren().add(rentalButton);
        primaryStage.setScene(scene2);
        primaryStage.show();
    }

    private void createFilmPage(Stage primaryStage){
        ComboBox comboBox = new ComboBox(olFilmTitles);
        comboBox.setPromptText("Film titlar");

        VBox vbox = new VBox();
        Button returnToHome = new Button();
        returnToHome.setLayoutX(250);
        returnToHome.setLayoutY(220);
        returnToHome.setText("Return");
        returnToHome.setOnAction(event -> {
            createHomeScene(primaryStage);
        });
        vbox.getChildren().addAll(comboBox, returnToHome);
        BorderPane filmBorderPane = new BorderPane(vbox);
        Scene scene3 = new Scene(filmBorderPane, 1280, 720);
        primaryStage.setScene(scene3);
        primaryStage.show();
    }

    private void createActorPage(Stage primaryStage){
        TableView actorTable = new TableView();
        TableColumn<Short, ActorEntity> col_actorID = new TableColumn<>("Actor ID");
        TableColumn<String, ActorEntity> col_firstName = new TableColumn<>("First Name");
        TableColumn<String, ActorEntity> col_lastName = new TableColumn<>("Last Name");
        TableColumn<Timestamp, ActorEntity> col_lastUpdate = new TableColumn<>("Last Update");

        col_actorID.setCellValueFactory(new PropertyValueFactory<>("actorId"));
        col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        col_lastUpdate.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));

        actorTable.getColumns().addAll(col_actorID, col_firstName, col_lastName, col_lastUpdate);

        for (int i = 0; i < olActors.size(); i++) {
            actorTable.getItems().add(olActors.get(i));
        }

        ComboBox comboBox = new ComboBox(olActorNames);
        comboBox.setPromptText("Skådespelare");

        VBox vbox = new VBox();
        Button returnToHome = new Button();
        returnToHome.setLayoutX(250);
        returnToHome.setLayoutY(220);
        returnToHome.setText("Return");
        returnToHome.setOnAction(event -> {
            createHomeScene(primaryStage);
        });
        vbox.getChildren().addAll(actorTable, comboBox, returnToHome);
        BorderPane filmBorderPane = new BorderPane(vbox);
        Scene scene4 = new Scene(filmBorderPane, 1280, 720);
        primaryStage.setScene(scene4);
        primaryStage.show();
    }

    private void createCustomerDbPage(Stage primaryStage){
        TableView customerTable = new TableView();
        TableColumn <Short, CustomerEntity> col_customerID = new TableColumn<>("Customer ID");
        TableColumn <Byte, CustomerEntity> col_StoreID= new TableColumn<>("Store ID");
        TableColumn <String, CustomerEntity> col_firstName = new TableColumn<>("First Name");
        TableColumn <String, CustomerEntity> col_lastName = new TableColumn<>("Last Name");
        TableColumn <String, CustomerEntity> col_email = new TableColumn<>("Email");
        TableColumn <Short, CustomerEntity> col_addressID = new TableColumn<>("Address ID");
        TableColumn <Boolean, CustomerEntity> col_activeMember = new TableColumn<>("Active");
        TableColumn <Timestamp, CustomerEntity> col_createDate = new TableColumn<>("Create Date");
        TableColumn <Timestamp, CustomerEntity> col_lastUpdate = new TableColumn<>("Last Update");

        col_customerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        col_StoreID.setCellValueFactory(new PropertyValueFactory<>("storeId"));
        col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_addressID.setCellValueFactory(new PropertyValueFactory<>("addressId"));
        col_activeMember.setCellValueFactory(new PropertyValueFactory<>("active"));
        col_createDate.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        col_lastUpdate.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));

        customerTable.getColumns().addAll(col_customerID, col_StoreID, col_firstName, col_lastName, col_email, col_addressID, col_activeMember, col_createDate, col_lastUpdate);

        for (int i = 0; i < olCustomer.size(); i++) {
            customerTable.getItems().add(olCustomer.get(i));
        }

        VBox vbox = new VBox();
        Button returnToHome = new Button();
        returnToHome.setLayoutX(250);
        returnToHome.setLayoutY(220);
        returnToHome.setText("Return");
        returnToHome.setOnAction(event -> {
            createHomeScene(primaryStage);
        });
        vbox.getChildren().addAll(customerTable, returnToHome);
        BorderPane customerBorderPane = new BorderPane(vbox);
        Scene scene5 = new Scene(customerBorderPane, 1280, 720);
        primaryStage.setScene(scene5);
        primaryStage.show();
    }


    private Boolean checkLogInCredentials(TextField tfUserName, TextField tfPassword, Label lLogInAnswer) {
        if(!tfUserName.getText().toString().equals("EFDB1") && !tfPassword.getText().toString().equals("123")){
            lLogInAnswer.setText("Wrong username or password!");
            return false;
        } else return true;
    }
}