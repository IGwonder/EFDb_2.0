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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.persistence.*;
import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EFDb extends Application {

    private static final EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("hibernate");

    static final ObservableList olFilms = FXCollections.observableArrayList();
    static final ObservableList olFilmTitles = FXCollections.observableArrayList();
    static final ObservableList olActorNames = FXCollections.observableArrayList();
    static final ObservableList olActors = FXCollections.observableArrayList();
    static final ObservableList olCustomer = FXCollections.observableArrayList();

    private static Stage stg;

    public static void main(String[] args) {

       getTablesFromDatabase();

        launch(args);
    }

    public static void getFilmTitles(EntityManager entityManager){
        Query query = entityManager.createNativeQuery("SELECT title FROM film");
        List<String> films = query.getResultList();
        for (String title : films){
            olFilmTitles.add(title);
        }
    }

    public static void getFilms(EntityManager entityManager){
        Query filmIDQuery = entityManager.createNativeQuery("SELECT film_id FROM film");
        Query filmTitleQuery = entityManager.createNativeQuery("SELECT title FROM film");
        Query filmDescriptionQuery = entityManager.createNativeQuery("SELECT description FROM film");
        Query filmReleaseYearQuery = entityManager.createNativeQuery("SELECT release_year FROM film");
        Query filmLanguageIDQuery = entityManager.createNativeQuery("SELECT language_id FROM film");
        Query filmOriginalLanguageIDQuery = entityManager.createNativeQuery("SELECT original_language_id FROM film");
        Query filmRentalDurationQuery = entityManager.createNativeQuery("SELECT rental_duration FROM film");
        Query filmRentalRateQuery = entityManager.createNativeQuery("SELECT rental_rate FROM film");
        Query filmLengthQuery = entityManager.createNativeQuery("SELECT length FROM film");
        Query filmReplacementCostQuery = entityManager.createNativeQuery("SELECT replacement_cost FROM film");
        Query filmRatingQuery = entityManager.createNativeQuery("SELECT rating FROM film");
        Query filmSpecialFeaturesQuery = entityManager.createNativeQuery("SELECT special_features FROM film");
        Query filmLastUpdateQuery = entityManager.createNativeQuery("SELECT last_update FROM film");

        List<Short> filmIDList = filmIDQuery.getResultList();
        List<String> filmTitleList = filmTitleQuery.getResultList();
        List<String> filmDescriptionList = filmDescriptionQuery.getResultList();
        List<Date> filmReleaseYearList = filmReleaseYearQuery.getResultList();
        List<Byte> filmLanguageIDList = filmLanguageIDQuery.getResultList();
        List<Byte> filmOriginalLanguageIDList = filmOriginalLanguageIDQuery.getResultList();
        List<Byte> filmRentalDurationList = filmRentalDurationQuery.getResultList();
        List<BigDecimal> filmRentalRateList = filmRentalRateQuery.getResultList();
        List<Short> filmLengthList = filmLengthQuery.getResultList();
        List<BigDecimal> filmReplacementCostList = filmReplacementCostQuery.getResultList();
        List<String> filmRatingList = filmRatingQuery.getResultList();
        List<String> filmSpecialFeaturesList = filmSpecialFeaturesQuery.getResultList();
        List<Timestamp> filmLastUpdateList = filmLastUpdateQuery.getResultList();

        for(int i = 0; i < filmIDList.size(); i++){
            Short filmID = filmIDList.get(i);
            String filmTitle = filmTitleList.get(i);
            String description = filmDescriptionList.get(i);
            Date releaseYear = filmReleaseYearList.get(i);
            Byte languageID = filmLanguageIDList.get(i);
            Byte originalLanguageID = filmOriginalLanguageIDList.get(i);
            Byte rentalDuration = filmRentalDurationList.get(i);
            BigDecimal rentalRate = filmRentalRateList.get(i);
            Short length = filmLengthList.get(i);
            BigDecimal replacementCost = filmReplacementCostList.get(i);
            String rating = filmRatingList.get(i);
            String specialFeatures = filmSpecialFeaturesList.get(i);
            Timestamp lastUpdate = filmLastUpdateList.get(i);

            FilmEntity film = new FilmEntity(filmID, filmTitle, description, releaseYear, languageID, originalLanguageID, rentalDuration, rentalRate, length, replacementCost, rating, specialFeatures, lastUpdate);
            olFilms.add(film);
        }
    }

    public static void getActorNames(EntityManager entityManager){

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

    public static void getActors(EntityManager entityManager){
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

    public static void getCustomers(EntityManager entityManager){
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
            String customerFirstName = customerFirstNameList.get(i);
            String customerLastName = customerLastNameList.get(i);
            String email = emailList.get(i);
            Short addressID = addressIDList.get(i);
            Boolean active = activeList.get(i);
            Timestamp dateList = createDateList.get(i);
            Timestamp lastUpdate = lastUpdateList.get(i);

            CustomerEntity customer = new CustomerEntity(customerID, storeID, customerFirstName, customerLastName, email, addressID, active, dateList, lastUpdate);
            olCustomer.add(customer);

        }

    }

    public static void getTablesFromDatabase(){
        EntityManager entityManager = emFactory.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            getFilms(entityManager);
            getFilmTitles(entityManager);
            getActors(entityManager);
            getActorNames(entityManager);
            getCustomers(entityManager);

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
        vbox.setAlignment(Pos.CENTER);
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
        AnchorPane homeAnchorPane = new AnchorPane();
        BorderPane homeBorderPane = new BorderPane(homeAnchorPane);
        HBox buttonBar = new HBox();
        homeBorderPane.setBottom(buttonBar);

        Button filmButton = new Button(buttonBar.toString());
        filmButton.setText("Filmer");
        filmButton.setLayoutX(450);
        filmButton.setLayoutY(300);
        filmButton.setOnAction(event -> {
            createFilmPage(primaryStage);
        });


        Button actorButton = new Button(buttonBar.toString());
        actorButton.setText("Skådespelare");
        actorButton.setLayoutX(450);
        actorButton.setLayoutY(300);
        actorButton.setOnAction(event -> {
            createActorPage(primaryStage);
        });


        Button customerDbButton = new Button(buttonBar.toString());
        customerDbButton.setText("Kunder");
        customerDbButton.setLayoutX(450);
        customerDbButton.setLayoutY(300);
        customerDbButton.setOnAction(event -> {
            createCustomerDbPage(primaryStage);
        });

        Scene scene2 = new Scene(homeBorderPane,1280,720);
        buttonBar.setAlignment(Pos.BOTTOM_CENTER);
        buttonBar.getChildren().add(filmButton);
        buttonBar.getChildren().add(actorButton);
        buttonBar.getChildren().add(customerDbButton);
        primaryStage.setScene(scene2);
        primaryStage.show();
    }

    private void createFilmPage(Stage primaryStage){
        TableView filmTable = new TableView();
        TableColumn<Short, FilmEntity> col_filmID = new TableColumn<>("Film ID");
        TableColumn<String, FilmEntity> col_title= new TableColumn<>("Title");
        TableColumn<String, FilmEntity> col_description= new TableColumn<>("Description");
        TableColumn<Date, FilmEntity> col_releaseYear= new TableColumn<>("Release Year");
        TableColumn<Byte, FilmEntity> col_languageID= new TableColumn<>("Language ID");
        TableColumn<Byte, FilmEntity> col_originalLanguageID= new TableColumn<>("Original Language ID");
        TableColumn<Byte, FilmEntity> col_rentalDuration= new TableColumn<>("Rental Duration");
        TableColumn<BigDecimal, FilmEntity> col_rentalRate = new TableColumn<>("Rental Rate");
        TableColumn<Short, FilmEntity> col_length = new TableColumn<>("Length");
        TableColumn<BigDecimal, FilmEntity> col_replacementCost = new TableColumn<>("Replacement Cost");
        TableColumn<String, FilmEntity> col_rating = new TableColumn<>("Rating");
        TableColumn<String, FilmEntity> col_specialFeatures = new TableColumn<>("Special Features");
        TableColumn<Timestamp, FilmEntity> col_lastUpdate = new TableColumn<>("Last Update");

        col_filmID.setCellValueFactory(new PropertyValueFactory<>("filmId"));
        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_releaseYear.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
        col_languageID.setCellValueFactory(new PropertyValueFactory<>("languageId"));
        col_originalLanguageID.setCellValueFactory(new PropertyValueFactory<>("originalLanguageId"));
        col_rentalDuration.setCellValueFactory(new PropertyValueFactory<>("rentalDuration"));
        col_rentalRate.setCellValueFactory(new PropertyValueFactory<>("rentalRate"));
        col_length.setCellValueFactory(new PropertyValueFactory<>("length"));
        col_replacementCost.setCellValueFactory(new PropertyValueFactory<>("replacementCost"));
        col_rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        col_specialFeatures.setCellValueFactory(new PropertyValueFactory<>("specialFeatures"));
        col_lastUpdate.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));

        filmTable.getColumns().addAll(col_filmID, col_title, col_description, col_releaseYear, col_languageID, col_originalLanguageID, col_rentalDuration, col_rentalRate, col_length, col_replacementCost, col_rating, col_specialFeatures, col_lastUpdate);

        for (int i = 0; i < olFilms.size(); i++){
            filmTable.getItems().add(olFilms.get(i));
        }

        ComboBox comboBox = new ComboBox(olFilmTitles);
        comboBox.setPromptText("Film titlar");

        Label customerSearch = new Label();
        customerSearch.setText("Search for movie: ");
        customerSearch.setLayoutX(350);
        customerSearch.setLayoutY(200);
        TextField searchField = new TextField();
        searchField.setPromptText("Search... ");

        VBox vbox = new VBox();
        Button returnToHome = new Button();
        returnToHome.setLayoutX(250);
        returnToHome.setLayoutY(220);
        returnToHome.setText("Return");
        returnToHome.setOnAction(event -> {
            createHomeScene(primaryStage);
        });
        vbox.getChildren().addAll(customerSearch, searchField, filmTable, comboBox, returnToHome);
        BorderPane filmBorderPane = new BorderPane(vbox);
        Scene scene3 = new Scene(filmBorderPane, 1280, 720);
        primaryStage.setScene(scene3);
        primaryStage.show();
    }

    private void createActorPage(Stage primaryStage){
        TextField actorFilterField = new TextField();

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

        Label customerSearch = new Label();
        customerSearch.setText("Search for actor: ");
        customerSearch.setLayoutX(350);
        customerSearch.setLayoutY(200);
        TextField searchField = new TextField();
        searchField.setPromptText("Search... ");

        VBox vbox = new VBox();
        Button returnToHome = new Button();
        returnToHome.setLayoutX(250);
        returnToHome.setLayoutY(220);
        returnToHome.setText("Return");
        returnToHome.setOnAction(event -> {
            createHomeScene(primaryStage);
        });
        vbox.getChildren().addAll(customerSearch, searchField, actorTable, comboBox, actorFilterField, returnToHome);
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
        AnchorPane anchorPane = new AnchorPane();
        VBox vbox = new VBox(anchorPane);

        Label customerSearch = new Label();
        customerSearch.setText("Search for customer: ");
        customerSearch.setLayoutX(350);
        customerSearch.setLayoutY(200);
        TextField searchField = new TextField();
        searchField.setPromptText("Search... ");

        Button addCustomerButton = new Button();
        addCustomerButton.setLayoutX(250);
        addCustomerButton.setLayoutY(220);
        addCustomerButton.setText("Add Customer");
        addCustomerButton.setAlignment(Pos.CENTER_LEFT);
        addCustomerButton.setOnAction(event -> {
            CustomerEntity newCustomer = new CustomerEntity();
            createAddCustomerScene(primaryStage, customerTable);
        });
        Button returnToHome = new Button();
        returnToHome.setLayoutX(250);
        returnToHome.setLayoutY(220);
        returnToHome.setText("Return");
        returnToHome.setOnAction(event -> {
            createHomeScene(primaryStage);
        });
        vbox.getChildren().addAll(customerSearch, searchField, customerTable, addCustomerButton, returnToHome);
        BorderPane customerBorderPane = new BorderPane(vbox);
        Scene scene5 = new Scene(customerBorderPane, 1280, 720);
        primaryStage.setScene(scene5);
        primaryStage.show();
    }

    public static void addToDatabase(TextField storeID, TextField firstName, TextField lastName, TextField email, TextField active, TextField address, TextField district, TextField city, TextField country, TextField phone, TextField longitude, TextField latitude){
        EntityManager entityManager = emFactory.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Query queryAddressID = entityManager.createNativeQuery("SELECT address_id from address where address = '" + address.getText() + "'");
            Query queryCityID = entityManager.createNativeQuery("SELECT city_id from city where city = '" + city.getText() + "'");
            Query queryCountryID = entityManager.createNativeQuery("SELECT country_id from country where country = '" + country.getText() + "'");

            CountryEntity newCustomerCountry = new CountryEntity(country.getText(), Timestamp.valueOf(LocalDateTime.now()));
            entityManager.persist(newCustomerCountry);
            CityEntity newCustomerCity = new CityEntity(city.getText(),(short) queryCountryID.getFirstResult(), Timestamp.valueOf(LocalDateTime.now()));
//            entityManager.persist(newCustomerCity);
            Query queryCity = entityManager.createNativeQuery("INSERT INTO city (city, country_id, last_update) VALUES ('"+city.getText()+"','"+(short) queryCountryID.getFirstResult()+"', '2008-01-01 00:00:01')");
            Query queryAddress = entityManager.createNativeQuery("INSERT INTO address (address, address2, district, city_id, postal_code, phone, location, last_update) VALUES ('"+address.getText()+"', null, '"+district.getText()+"', '"+(short) queryCityID.getFirstResult()+"', 43145, 07398765, POINT(10.0, 5.0),'2008-01-01 00:00:01' /*Timestamp.valueOf(LocalDateTime.now())*/)");
//
//
//            CustomerEntity newCustomer = new CustomerEntity(Byte.parseByte(storeID.getText()), firstName.getText(), lastName.getText(), email.getText(),(short) queryAddressID.getFirstResult() ,Boolean.parseBoolean(active.getText()), Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()));
//            AddressEntity newCustomerAddress = new AddressEntity(address.getText(), district.getText(),(short) queryCityID.getFirstResult(), phone.getText(), Double.parseDouble(longitude.getText()), Double.parseDouble(latitude.getText()), Timestamp.valueOf(LocalDateTime.now()));


//            entityManager.persist(newCustomer);
//            entityManager.persist(newCustomerAddress);



            transaction.commit();

            olCustomer.clear();
            getCustomers(entityManager);

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

        TextField addressID = new TextField();
        addressID.setPromptText("address ID");

        TextField storeID = new TextField();
        storeID.setText("1");

        TextField active = new TextField();
        active.setText("true");

        TextField city = new TextField();
        city.setText("Houston");

        TextField district = new TextField();
        district.setText("District");

        TextField lastUpdate = new TextField();
        lastUpdate.setPromptText("last update");

        TextField country = new TextField();
        country.setText("Sweden");

        TextField phone = new TextField();
        phone.setPromptText("Phone");

        TextField longitude = new TextField();
        longitude.setText("-26.66115");

        TextField latitude = new TextField();
        latitude.setText("40.95858");

        Button registerCustomer = new Button();
        registerCustomer.setLayoutX(250);
        registerCustomer.setLayoutY(220);
        registerCustomer.setText("Register Customer");
        registerCustomer.setOnAction(event -> {
          //TODO: gör om att ny kund läggs till i databasen och tabellen ska uppdateras därefter!
            addToDatabase(storeID, firstName, lastName, email, active, address, district, city, country, phone, longitude, latitude);
        });

        Button returnToCustomerScene = new Button();
        returnToCustomerScene.setLayoutX(250);
        returnToCustomerScene.setLayoutY(220);
        returnToCustomerScene.setText("Return");
        returnToCustomerScene.setOnAction(event -> {
            createCustomerDbPage(primaryStage);
        });
        vbox.getChildren().addAll(returnToCustomerScene, firstName, lastName, email, address, addressID, storeID, active, city, lastUpdate, country, phone, longitude, latitude, registerCustomer);
        BorderPane customerBorderPane = new BorderPane(vbox);
        Scene scene7 = new Scene(customerBorderPane, 1280, 720);
        primaryStage.setScene(scene7);
        primaryStage.show();
    }


    private Boolean checkLogInCredentials(TextField tfUserName, TextField tfPassword, Label lLogInAnswer) {
        if(!tfUserName.getText().toString().equals("EFDB1") && !tfPassword.getText().toString().equals("123")){
            lLogInAnswer.setText("Wrong username or password!");
            return false;
        } else return true;
    }
}
