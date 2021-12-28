package org.example.EFDb;

import Entities.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.persistence.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.math.BigInteger;

public class EFDb extends Application {

    private static final EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("hibernate");

    protected static final ObservableList<HomePageInfoEntity> olHome = FXCollections.observableArrayList();
    protected static final ObservableList olTop10Films = FXCollections.observableArrayList();
    protected static final ObservableList<FilmEntity> olFilms = FXCollections.observableArrayList();
    protected static final ObservableList olFilmTitles = FXCollections.observableArrayList();
    protected static final ObservableList olActorNames = FXCollections.observableArrayList();
    protected static final ObservableList<ActorEntity> olActors = FXCollections.observableArrayList();
    protected static final ObservableList olCustomer = FXCollections.observableArrayList();
    protected static final ObservableList<PaymentRentalEntity> olPaymentRental = FXCollections.observableArrayList();
    protected static final ObservableList olLanguage = FXCollections.observableArrayList();

    public static void main(String[] args) {
        GetFromDatabase get = new GetFromDatabase();
        EntityManager entityManager = emFactory.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            get.Films(entityManager);
            get.FilmTitles(entityManager);
            get.Actors(entityManager);
            get.ActorNames(entityManager);
            get.Customers(entityManager);
            get.PaymentRentalInfo(entityManager);
            get.Language(entityManager);
            get.HomePageInfo(entityManager);

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
        VBox vbox = new VBox();

        TextField tfUserName = new TextField();
        tfUserName.setPromptText("Username");
        PasswordField tfPassword = new PasswordField();
        tfPassword.setPromptText("Password");
        Button bLogin = new Button();
        bLogin.setText("Log in");
        Label lLogInAnswer = new Label();
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(tfUserName, tfPassword, bLogin, lLogInAnswer);

        BorderPane logIn = new BorderPane(vbox);

        Scene scene = new Scene(logIn, 200, 200);
        primaryStage.setTitle("EFDb");
        primaryStage.setScene(scene);

        bLogin.setOnAction(event -> {
            if(checkLogInCredentials(tfUserName, tfPassword, lLogInAnswer)){
                createHomeScene(primaryStage);
            }
            tfUserName.clear();
            tfPassword.clear();
        });

        primaryStage.show();
    }

    private void createHomeScene(Stage primaryStage) {
        TableView homeTable = new TableView();
        TableView top10Table = new TableView<>();
        TableColumn<String, HomePageInfoEntity> col_title= new TableColumn<>("Title");
        TableColumn<Date, HomePageInfoEntity> col_releaseYear= new TableColumn<>("Release Date");
        TableColumn<Short, HomePageInfoEntity> col_length = new TableColumn<>("Length");
        TableColumn<String, HomePageInfoEntity> col_language= new TableColumn<>("Language");
        TableColumn<String, HomePageInfoEntity> col_rating = new TableColumn<>("Rating");
        TableColumn<BigInteger, HomePageInfoEntity> col_totalCopies = new TableColumn<>("Total Copies Available");
        TableColumn<Integer, HomePageInfoEntity> col_availableCopies = new TableColumn<>("Number of Copies Available");
        TableColumn<String, HomePageInfoEntity> col_top10Films = new TableColumn<>("Top 10 of most rented films");

        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_releaseYear.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
        col_language.setCellValueFactory(new PropertyValueFactory<>("language"));
        col_length.setCellValueFactory(new PropertyValueFactory<>("length"));
        col_rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        col_totalCopies.setCellValueFactory(new PropertyValueFactory<>("totalCopies"));
        col_availableCopies.setCellValueFactory(new PropertyValueFactory<>("availableCopies"));
        col_top10Films.setCellValueFactory(new PropertyValueFactory<>("top10Films"));

        homeTable.getColumns().addAll(col_title, col_releaseYear, col_language, col_length, col_rating, col_totalCopies, col_availableCopies);
        top10Table.getColumns().addAll(col_top10Films);

        for (int i = 0; i < olHome.size(); i++){
            homeTable.getItems().addAll(olHome.get(i));
        }
        for (int i = 0; i < olTop10Films.size(); i++){
            top10Table.getItems().add(olTop10Films.get(i));
        }

        HBox buttonBar = new HBox();

        Button filmButton = new Button(buttonBar.toString());
        filmButton.setText("Filmer");
        filmButton.setLayoutX(250);
        filmButton.setLayoutY(180);
        filmButton.setOnAction(event -> {
            createFilmPage(primaryStage);
        });

        Button actorButton = new Button(buttonBar.toString());
        actorButton.setText("Skådespelare");
        actorButton.setLayoutX(250);
        actorButton.setLayoutY(180);
        actorButton.setOnAction(event -> {
            createActorPage(primaryStage);
        });

        Button customerDbButton = new Button(buttonBar.toString());
        customerDbButton.setText("Kunder");
        customerDbButton.setLayoutX(250);
        customerDbButton.setLayoutY(180);
        customerDbButton.setOnAction(event -> {
            createCustomerDbPage(primaryStage);
        });

        Button rentalButton = new Button(buttonBar.toString());
        rentalButton.setText("Rental");
        rentalButton.setLayoutX(250);
        rentalButton.setLayoutY(180);
        rentalButton.setOnAction(event -> {
            createRentalPage(primaryStage);
        });

        buttonBar.setAlignment(Pos.BOTTOM_CENTER);
        buttonBar.getChildren().addAll(filmButton, actorButton, customerDbButton, rentalButton);

        VBox vBoxTop10Table = new VBox(top10Table);
        VBox vBoxSearch = new VBox();
        TextField tfSearchFilm = new TextField();
        TextField tfFilmResult = new TextField();
        TextField tfRentalRate = new TextField();
        tfSearchFilm.setPromptText("Search film title");
        tfFilmResult.setPromptText("Available Copies");
        tfRentalRate.setPromptText("Rental Rate");
        Button searchButton = new Button("Search...");
        vBoxSearch.getChildren().addAll(tfSearchFilm, tfFilmResult, tfRentalRate, searchButton);
        searchButton.setOnAction(event -> {
            for (HomePageInfoEntity hpObj : olHome){
                if (tfSearchFilm.getText().equalsIgnoreCase(hpObj.getTitle())) {
                    tfFilmResult.setText("Available Copies: "+hpObj.getAvailableCopies());
                    tfRentalRate.setText("Rental Rate: "+hpObj.getRentalRate()+" USD");
                }
            }
        });
        HBox hBoxTableAndSearch = new HBox();
        hBoxTableAndSearch.getChildren().addAll(homeTable,vBoxSearch);

        BorderPane homeBorderPane = new BorderPane();
        homeBorderPane.setLeft(hBoxTableAndSearch);
        homeBorderPane.setRight(vBoxTop10Table);
        homeBorderPane.setBottom(buttonBar);
        Scene scene2 = new Scene(homeBorderPane,1280,720);
        primaryStage.setScene(scene2);
        primaryStage.show();
    }

    private void createRentalPage(Stage primaryStage) {
        TableView rentalTable = new TableView();
        TextField searchBar = new TextField();
        TextField resultBar = new TextField();
        resultBar.setText("Rented films: ");
        Label searchForCustomer = new Label();
        searchForCustomer.setLayoutX(100);
        searchForCustomer.setLayoutY(80);
        searchForCustomer.setText("Search for Customer");
        searchBar.setPromptText("Search for Customer ID");
        Button searchButton = new Button();
        searchButton.setText("Search");
        TableColumn<Integer, PaymentRentalEntity> col_rentalId = new TableColumn<>("Rental ID");
        TableColumn<Timestamp, PaymentRentalEntity> col_rentalDate= new TableColumn<>("Rental Date");
        TableColumn<Short, PaymentRentalEntity> col_filmId= new TableColumn<>("Film ID");
        TableColumn<Integer, PaymentRentalEntity> col_customerId= new TableColumn<>("Customer ID");
        TableColumn<Timestamp, PaymentRentalEntity> col_returnDate= new TableColumn<>("Return Date");
        TableColumn<Byte, PaymentRentalEntity> col_staffId= new TableColumn<>("Staff ID");
        TableColumn<Short, PaymentRentalEntity> col_paymentId= new TableColumn<>("Payment ID");
        TableColumn<BigDecimal, PaymentRentalEntity> col_amount= new TableColumn<>("Price");
        TableColumn<Timestamp, PaymentRentalEntity> col_paymentDate= new TableColumn<>("Payment Date");


        col_rentalId.setCellValueFactory(new PropertyValueFactory<>("rentalId"));
        col_rentalDate.setCellValueFactory(new PropertyValueFactory<>("rentalDate"));
        col_filmId.setCellValueFactory(new PropertyValueFactory<>("filmId"));
        col_customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        col_returnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        col_staffId.setCellValueFactory(new PropertyValueFactory<>("staffId"));
        col_paymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        col_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        col_paymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));

        rentalTable.getColumns().addAll(col_rentalId, col_rentalDate, col_filmId, col_customerId, col_returnDate, col_staffId, col_amount, col_paymentId, col_paymentDate);

        for (int i = 0; i < olPaymentRental.size(); i++){
            rentalTable.getItems().add(olPaymentRental.get(i));
        }
        searchButton.setOnAction(event -> {
            StringBuilder sb = new StringBuilder();
            Set<Short> filmIdList = new HashSet<>();
            for (PaymentRentalEntity paymentRentalInfo : olPaymentRental){
                if(searchBar.getText().equals(paymentRentalInfo.getCustomerId().toString())){
                    filmIdList.add(paymentRentalInfo.getFilmId());
                }
            }
            for (Short filmId : filmIdList){
                sb.append(filmId.toString()+", ");
            }
            resultBar.setText("Rented films: "+sb.toString());
        });

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        Button returnToHome = new Button();
        returnToHome.setLayoutX(250);
        returnToHome.setLayoutY(220);
        returnToHome.setText("Return");
        returnToHome.setOnAction(event -> {
            createHomeScene(primaryStage);
        });
        vBox.getChildren().addAll(searchForCustomer, searchBar, resultBar, searchButton, rentalTable, returnToHome);
        BorderPane rentalBorderPane = new BorderPane(vBox);
        Scene scene9 = new Scene(rentalBorderPane, 1280, 720);
        primaryStage.setScene(scene9);

    }

    private void createFilmPage(Stage primaryStage){
        TableView filmTable = new TableView();
        TableColumn<Short, FilmEntity> col_filmID = new TableColumn<>("Film ID");
        TableColumn<String, FilmEntity> col_title= new TableColumn<>("Title");
        TableColumn<String, FilmEntity> col_description= new TableColumn<>("Description");
        TableColumn<Date, FilmEntity> col_releaseYear= new TableColumn<>("Release Year");
        TableColumn<String, FilmEntity> col_actorName = new TableColumn<>("Actor Names");
        TableColumn<Short, FilmEntity> col_length = new TableColumn<>("Length");
        TableColumn<BigDecimal, FilmEntity> col_replacementCost = new TableColumn<>("Replacement Cost");
        TableColumn<String, FilmEntity> col_rating = new TableColumn<>("Rating");
        TableColumn<String, FilmEntity> col_specialFeatures = new TableColumn<>("Special Features");
        TableColumn<Timestamp, FilmEntity> col_lastUpdate = new TableColumn<>("Last Update");

        col_filmID.setCellValueFactory(new PropertyValueFactory<>("filmId"));
        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_releaseYear.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
        col_actorName.setCellValueFactory(new PropertyValueFactory<>("actorName"));
        col_length.setCellValueFactory(new PropertyValueFactory<>("length"));
        col_replacementCost.setCellValueFactory(new PropertyValueFactory<>("replacementCost"));
        col_rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        col_specialFeatures.setCellValueFactory(new PropertyValueFactory<>("specialFeatures"));
        col_lastUpdate.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));

        filmTable.getColumns().addAll(col_filmID, col_title, col_description, col_releaseYear, col_actorName, col_length, col_replacementCost, col_rating, col_specialFeatures, col_lastUpdate);

        for (int i = 0; i < olFilms.size(); i++){
            filmTable.getItems().add(olFilms.get(i));
        }

        ComboBox comboBox = new ComboBox(olFilmTitles);
        comboBox.setPromptText("Film titles");

        TextField resultField = new TextField();
        resultField.setText("Actor in films: ");

        Button searchButton = new Button();
        searchButton.setLayoutX(250);
        searchButton.setLayoutX(220);
        searchButton.setText("Search");
        searchButton.setOnAction(event -> {
            comboBox.getValue();
            for (FilmEntity film : olFilms){
                if (comboBox.getValue().equals(film.getTitle())){
                    resultField.setText("Actor in Films: " + film.getActorName());
                }
            }
        });

        VBox vbox = new VBox();
        Button returnToHome = new Button();
        returnToHome.setLayoutX(250);
        returnToHome.setLayoutY(220);
        returnToHome.setText("Return");
        returnToHome.setOnAction(event -> {
            createHomeScene(primaryStage);
        });

        vbox.getChildren().addAll(filmTable, comboBox, resultField, searchButton, returnToHome);
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
        TableColumn<String, ActorEntity> col_actorMovies = new TableColumn<>("Movies");

        col_actorID.setCellValueFactory(new PropertyValueFactory<>("actorId"));
        col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        col_lastUpdate.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        col_actorMovies.setCellValueFactory(new PropertyValueFactory<>("actorFilms"));

        actorTable.getColumns().addAll(col_actorID, col_firstName, col_lastName, col_actorMovies, col_lastUpdate);

        for (int i = 0; i < olActors.size(); i++) {
            actorTable.getItems().add(olActors.get(i));
        }

        ComboBox comboBox = new ComboBox(olActorNames);
        comboBox.setPromptText("Skådespelare");

        VBox vbox = new VBox();

        TextField actorFilterField = new TextField();

        Button searchButton = new Button();
        searchButton.setText("Search..");
        searchButton.setLayoutX(250);
        searchButton.setLayoutY(220);
        searchButton.setOnAction(event -> {
            comboBox.getValue();
            for (ActorEntity actor : olActors){
                if (comboBox.getValue().equals(actor.getFirstName()+" "+actor.getLastName())){
                    actorFilterField.setText(actor.getActorFilms());
                }
            }
        });

        Button returnToHome = new Button();
        returnToHome.setLayoutX(250);
        returnToHome.setLayoutY(220);
        returnToHome.setText("Return");
        returnToHome.setOnAction(event -> {
            createHomeScene(primaryStage);
        });

        vbox.getChildren().addAll(actorTable, comboBox, searchButton, actorFilterField, returnToHome);

        BorderPane filmBorderPane = new BorderPane(vbox);
        Scene scene4 = new Scene(filmBorderPane, 1280, 720);
        primaryStage.setScene(scene4);
        primaryStage.show();
    }

    private void createCustomerDbPage(Stage primaryStage){
        TableView customerTable = new TableView();
        TableColumn <Integer, CustomerEntity> col_customerID = new TableColumn<>("Customer ID");
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
        AnchorPane anchorPane = new AnchorPane();
        VBox vbox = new VBox(anchorPane);

        Button addCustomerButton = new Button();
        addCustomerButton.setLayoutX(250);
        addCustomerButton.setLayoutY(220);
        addCustomerButton.setText("Add Customer");
        addCustomerButton.setAlignment(Pos.CENTER_LEFT);
        addCustomerButton.setOnAction(event -> {
            createAddCustomerScene(primaryStage, customerTable);
        });

        TextField tfCustomerEmail = new TextField();
        tfCustomerEmail.setPromptText("Change email");


        TextField tfCustomerID = new TextField();
        tfCustomerID.setPromptText("Customer ID");

        Button updateCustomer = new Button();
        updateCustomer.setLayoutX(250);
        updateCustomer.setLayoutY(220);
        updateCustomer.setText("Update Customer");
        updateCustomer.setAlignment(Pos.CENTER_LEFT);
        updateCustomer.setOnAction(event -> {
            updateCustomer(tfCustomerEmail, tfCustomerID);
        });

        Button deleteCustomer = new Button();
        deleteCustomer.setLayoutX(250);
        deleteCustomer.setLayoutY(220);
        deleteCustomer.setText("Delete");
        deleteCustomer.setAlignment(Pos.CENTER_LEFT);
        deleteCustomer.setOnAction(event -> {
            deleteFromDatabase(tfCustomerID);
        });

        Button returnToHome = new Button();
        returnToHome.setLayoutX(250);
        returnToHome.setLayoutY(220);
        returnToHome.setText("Return");
        returnToHome.setOnAction(event -> {
            createHomeScene(primaryStage);
        });
        vbox.getChildren().addAll(customerTable, addCustomerButton, tfCustomerID, tfCustomerEmail, updateCustomer, deleteCustomer, returnToHome);
        BorderPane customerBorderPane = new BorderPane(vbox);
        Scene scene5 = new Scene(customerBorderPane, 1280, 720);
        primaryStage.setScene(scene5);
        primaryStage.show();
    }

    private void updateCustomer(TextField tfCustomerEmail, TextField tfCustomerID) {
        GetFromDatabase get = new GetFromDatabase();
        EntityManager entityManager = emFactory.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.createNativeQuery("UPDATE customer SET email = '"+tfCustomerEmail.getText()+"' WHERE customer_id = '"+tfCustomerID.getText()+"'").executeUpdate();

            transaction.commit();
            olCustomer.clear();
            get.Customers(entityManager);

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        } finally {
            entityManager.close();
        }
    }

    public static void deleteFromDatabase(TextField tfCustomerID){
        GetFromDatabase get = new GetFromDatabase();
        EntityManager entityManager = emFactory.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.createNativeQuery("UPDATE customer SET active = 0 WHERE customer_id = '"+tfCustomerID.getText()+"'").executeUpdate();

            transaction.commit();
            olCustomer.clear();
            get.Customers(entityManager);

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        } finally {
            entityManager.close();
        }
    }

    public static void addToDatabase(TextField storeID, TextField firstName, TextField lastName, TextField email, TextField address, TextField postalCode, TextField district, TextField city, TextField country, TextField phone, TextField longitude, TextField latitude){
        GetFromDatabase get = new GetFromDatabase();
        EntityManager entityManager = emFactory.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Query queryCountryID = entityManager.createNativeQuery("SELECT country_id from country where country = '"+country.getText()+"'");

//            CityEntity newCustomerCity = new CityEntity(city.getText(),(short) queryCountryID.getFirstResult(), Timestamp.valueOf(LocalDateTime.now()));EF
//            entityManager.persist(newCustomerCity);

            entityManager.createNativeQuery("INSERT INTO city (city, country_id, last_update) VALUES ('"+city.getText()+"','"+queryCountryID.getFirstResult()+"','"+Timestamp.valueOf(LocalDateTime.now())+"')")/*.executeUpdate()*/;
            Query queryCityID = entityManager.createNativeQuery("SELECT city_id from city where city = '"+city.getText()+"'");
            entityManager.createNativeQuery("INSERT INTO address (address, address2, district, city_id, postal_code, phone, location, last_update) VALUES ('"+address.getText()+"', null, '"+district.getText()+"', '"+(short)queryCityID.getFirstResult()+"', '"+postalCode.getText()+"', '"+phone.getText()+"', POINT(10.0, 5.0), '"+Timestamp.valueOf(LocalDateTime.now())+"')")/*.executeUpdate()*/;

//            AddressEntity newCustomerAddress = new AddressEntity(address.getText(), null, district.getText(),(short) 20, "4333", phone.getText(), Double.parseDouble(longitude.getText()), Double.parseDouble(latitude.getText()), Timestamp.valueOf(LocalDateTime.now()));
//            entityManager.persist(newCustomerAddress);

            Query queryAddressID = entityManager.createNativeQuery("SELECT address_id from address where address = '" + address.getText() + "'");
//            Query NEWCUST  = entityManager.createNativeQuery("INSERT INTO customer (store_id, first_name, last_name, email, address_id, active, create_date, last_update) VALUES (1, 'Ismar', 'Gutic', 'email.com', 5, 1, '"+Timestamp.valueOf(LocalDateTime.now())+"', '"+Timestamp.valueOf(LocalDateTime.now())+"')");
            CustomerEntity newCustomer = new CustomerEntity(Byte.parseByte(storeID.getText()), firstName.getText(), lastName.getText(), email.getText(),(short) 606 , true, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()));
            entityManager.persist(newCustomer);



            transaction.commit();
            olCustomer.clear();
            get.Customers(entityManager);

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        } finally {
            entityManager.close();
        }
    }

    private void createAddCustomerScene(Stage primaryStage, TableView customerTable){
        VBox vbox = new VBox();

        TextField firstName = new TextField();
        firstName.setText("First Name");

        TextField lastName = new TextField();
        lastName.setText("Last Name");

        TextField email = new TextField();
        email.setText("Megatron@protonmail.com");

        TextField address = new TextField();
        address.setText("Fake street 33");

        TextField postalCode = new TextField();
        postalCode.setText("12345");

        TextField storeID = new TextField();
        storeID.setText("1");

        TextField city = new TextField();
        city.setText("Mölndal");

        TextField district = new TextField();
        district.setText("Västra Götaland");

        TextField country = new TextField();
        country.setText("Sweden");

        TextField phone = new TextField();
        phone.setText("0739876508");

        TextField longitude = new TextField();
        longitude.setText("-26.66115");

        TextField latitude = new TextField();
        latitude.setText("40.95858");

        Button registerCustomer = new Button();
        registerCustomer.setLayoutX(250);
        registerCustomer.setLayoutY(220);
        registerCustomer.setText("Register Customer");
        registerCustomer.setOnAction(event -> {
            addToDatabase(storeID, firstName, lastName, email, address, postalCode, district, city, country, phone, longitude, latitude);
        });

        Button returnToCustomerScene = new Button();
        returnToCustomerScene.setLayoutX(250);
        returnToCustomerScene.setLayoutY(220);
        returnToCustomerScene.setText("Return");
        returnToCustomerScene.setOnAction(event -> {
            createCustomerDbPage(primaryStage);
        });
        vbox.getChildren().addAll(returnToCustomerScene, firstName, lastName, email, address, postalCode, storeID, city, district, country, phone, longitude, latitude, registerCustomer);
        BorderPane customerBorderPane = new BorderPane(vbox);
        Scene scene7 = new Scene(customerBorderPane, 1280, 720);
        primaryStage.setScene(scene7);
        primaryStage.show();
    }


    private Boolean checkLogInCredentials(TextField tfUserName, TextField tfPassword, Label lLogInAnswer) {
        if((!tfUserName.getText().toString().equals("EFDB1") && !tfPassword.getText().toString().equals("123")) || (!tfUserName.getText().toString().equals("EFDB1") && tfPassword.getText().toString().equals("123")) || (tfUserName.getText().toString().equals("EFDB1") && !tfPassword.getText().toString().equals("123"))){
            lLogInAnswer.setText("Wrong username or password!");
            return false;
        } else return true;
    }
}
