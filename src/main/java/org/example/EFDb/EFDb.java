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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.math.BigInteger;

public class EFDb extends Application {

    private static final EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("hibernate");

    static final ObservableList<HomePageInfoEntity> olHome = FXCollections.observableArrayList();
    static final ObservableList olTop10Films = FXCollections.observableArrayList();
    static final ObservableList<FilmEntity> olFilms = FXCollections.observableArrayList();
    static final ObservableList olFilmTitles = FXCollections.observableArrayList();
    static final ObservableList olActorNames = FXCollections.observableArrayList();
    static final ObservableList<ActorEntity> olActors = FXCollections.observableArrayList();
    static final ObservableList olCustomer = FXCollections.observableArrayList();
    static final ObservableList olRental = FXCollections.observableArrayList();
    static final ObservableList<PaymentRentalEntity> olPaymentRental = FXCollections.observableArrayList();
    static final ObservableList olLanguage = FXCollections.observableArrayList();
    ObservableList<FilmEntity> olFilmActor = FXCollections.observableArrayList();


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
        Query filmLengthQuery = entityManager.createNativeQuery("SELECT length FROM film");
        Query filmReplacementCostQuery = entityManager.createNativeQuery("SELECT replacement_cost FROM film");
        Query filmRatingQuery = entityManager.createNativeQuery("SELECT rating FROM film");
        Query filmSpecialFeaturesQuery = entityManager.createNativeQuery("SELECT special_features FROM film");
        Query filmLastUpdateQuery = entityManager.createNativeQuery("SELECT last_update FROM film");

        Query actorFilmIDQuery = entityManager.createNativeQuery("SELECT film.film_id FROM film, actor, film_actor WHERE film.film_id = film_actor.film_id AND film_actor.actor_id = actor.actor_id GROUP BY film_actor.film_id;");
        Query filmFirstNameQuery = entityManager.createNativeQuery("SELECT first_name FROM film, actor, film_actor WHERE film.film_id = film_actor.film_id AND film_actor.actor_id = actor.actor_id GROUP BY film_actor.film_id;");
        Query filmLastNameQuery = entityManager.createNativeQuery("SELECT last_name FROM film, actor, film_actor WHERE film.film_id = film_actor.film_id AND film_actor.actor_id = actor.actor_id GROUP BY film_actor.film_id;");

        List<Short> filmIDList = filmIDQuery.getResultList();
        List<String> filmTitleList = filmTitleQuery.getResultList();
        List<String> filmDescriptionList = filmDescriptionQuery.getResultList();
        List<Date> filmReleaseYearList = filmReleaseYearQuery.getResultList();
        List<Short> filmLengthList = filmLengthQuery.getResultList();
        List<BigDecimal> filmReplacementCostList = filmReplacementCostQuery.getResultList();
        List<String> filmRatingList = filmRatingQuery.getResultList();
        List<String> filmSpecialFeaturesList = filmSpecialFeaturesQuery.getResultList();
        List<Timestamp> filmLastUpdateList = filmLastUpdateQuery.getResultList();
        List<Short> actorFilmIDList = actorFilmIDQuery.getResultList();
        List<String> filmFirstNameList = filmFirstNameQuery.getResultList();
        List<String> filmLastNameList = filmLastNameQuery.getResultList();

        ObservableList<FilmEntity> olFilmActor = FXCollections.observableArrayList();
        for (int i = 0; i < actorFilmIDList.size(); i++){
            Short actorFilmID = actorFilmIDList.get(i);
            String actorFirstName = filmFirstNameList.get(i);
            String actorLastName = filmLastNameList.get(i);
            FilmEntity filmActor = new FilmEntity(actorFilmID, actorFirstName, actorLastName);
            olFilmActor.add(filmActor);
        }

        for(int i = 0; i < filmIDList.size(); i++){
            Short filmID = filmIDList.get(i);
            String filmTitle = filmTitleList.get(i);
            String description = filmDescriptionList.get(i);
            Date releaseYear = filmReleaseYearList.get(i);
            Short length = filmLengthList.get(i);
            BigDecimal replacementCost = filmReplacementCostList.get(i);
            String rating = filmRatingList.get(i);
            String specialFeatures = filmSpecialFeaturesList.get(i);
            Timestamp lastUpdate = filmLastUpdateList.get(i);
            String actorName = "";
            StringBuilder stringBuilder = new StringBuilder();
            for (FilmEntity filmActor : olFilmActor){
                if (i == filmActor.getActorFilmID()) {
                    stringBuilder.append(filmActor.getActorFirstName() + " " + filmActor.getActorLastName());

                }
            }
            actorName = stringBuilder.toString();

            FilmEntity film = new FilmEntity(filmID, filmTitle, description, releaseYear, length, replacementCost, rating, specialFeatures, lastUpdate, actorName);
            olFilms.add(film);
        }
    }

    public static void getHomePageInfo(EntityManager entityManager){
        Query filmIDQuery = entityManager.createNativeQuery("SELECT film_id FROM film");
        Query filmTitleQuery = entityManager.createNativeQuery("SELECT title FROM film INNER JOIN inventory ON film.film_id = inventory.film_id group by film.title;");
        Query filmReleaseYearQuery = entityManager.createNativeQuery("SELECT release_year FROM film INNER JOIN inventory ON film.film_id = inventory.film_id group by film.title;");
        Query languageNameQuery = entityManager.createNativeQuery("SELECT name FROM film INNER JOIN language ON film.language_id = language.language_id ORDER BY film.title;");
        Query filmRentalRateQuery = entityManager.createNativeQuery("SELECT rental_rate FROM film INNER JOIN inventory ON film.film_id = inventory.film_id group by film.title;");
        Query filmLengthQuery = entityManager.createNativeQuery("SELECT length FROM film INNER JOIN inventory ON film.film_id = inventory.film_id group by film.title;");
        Query filmRatingQuery = entityManager.createNativeQuery("SELECT rating FROM film INNER JOIN inventory ON film.film_id = inventory.film_id group by film.title;");
        Query totalCopiesQuery = entityManager.createNativeQuery("select count(film_id) from inventory group by film_id order by film_id asc;");
        Query missingFilmIdsQuery = entityManager.createNativeQuery("SELECT film_id FROM rental INNER JOIN inventory ON inventory.inventory_id = rental.inventory_id WHERE return_date is null group by film_id order by film_id;");
        Query numberOfMissingFilmCopiesQuery = entityManager.createNativeQuery("SELECT count(film_id) FROM rental INNER JOIN inventory ON inventory.inventory_id = rental.inventory_id WHERE return_date is null group by film_id order by film_id;");
        Query top10FilmsQuery = entityManager.createNativeQuery("SELECT title FROM inventory INNER JOIN rental ON rental.inventory_id = inventory.inventory_id INNER JOIN film ON film.film_id = inventory.film_id GROUP BY title ORDER BY count(rental_id) desc limit 10;");

        List<Short> filmIDList = filmIDQuery.getResultList();
        List<String> filmTitleList = filmTitleQuery.getResultList();
        List<Date> filmReleaseYearList = filmReleaseYearQuery.getResultList();
        List<String> languageNameList = languageNameQuery.getResultList();
        List<BigDecimal> filmRentalRateList = filmRentalRateQuery.getResultList();
        List<Short> filmLengthList = filmLengthQuery.getResultList();
        List<String> filmRatingList = filmRatingQuery.getResultList();
        List<BigInteger> totalCopiesList = totalCopiesQuery.getResultList();
        List<Short> missingFilmIdsList = missingFilmIdsQuery.getResultList();
        List<BigInteger> numberOfMissingFilmCopiesList = numberOfMissingFilmCopiesQuery.getResultList();
        List<String> top10FilmsList = top10FilmsQuery.getResultList();
        List<Integer> availableCopiesList = new ArrayList<>();

        ObservableList<HomePageInfoEntity> olMissingFilmCopies = FXCollections.observableArrayList();
        for (int i = 0; i < missingFilmIdsList.size(); i++){
            Short missingCopyFilmID = missingFilmIdsList.get(i);
            BigInteger numberOfMissingCopies = numberOfMissingFilmCopiesList.get(i);
            HomePageInfoEntity missingFilmCopy = new HomePageInfoEntity(missingCopyFilmID, numberOfMissingCopies);
            olMissingFilmCopies.add(missingFilmCopy);
        }


        for(int i = 0; i < filmTitleList.size(); i++){
            Short filmID = filmIDList.get(i);
            String filmTitle = filmTitleList.get(i);
            Date releaseYear = filmReleaseYearList.get(i);
            String languageName = languageNameList.get(i);
            BigDecimal rentalRate = filmRentalRateList.get(i);
            Short length = filmLengthList.get(i);
            String rating = filmRatingList.get(i);
            BigInteger totalCopies = totalCopiesList.get(i);
            int availableCopies = totalCopies.intValue();
            for (HomePageInfoEntity hpObj : olMissingFilmCopies){
                if (hpObj.getMissingCopyFilmID() == filmID){
                    availableCopies = totalCopies.intValue() - hpObj.getNumberOfMissingCopies().intValue();
                }
            }

            HomePageInfoEntity film = new HomePageInfoEntity(filmTitle, releaseYear, languageName, rentalRate, length, rating, totalCopies, availableCopies);
            olHome.add(film);
        }

        for (int i = 0; i < top10FilmsList.size(); i++){
            String top10Film = top10FilmsList.get(i);
            HomePageInfoEntity topFilm = new HomePageInfoEntity(top10Film);
            olTop10Films.add(topFilm);
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
        Query actorMoviesQuery = entityManager.createNativeQuery("SELECT title FROM film, actor, film_actor\n" +
                "WHERE film.film_id = film_actor.film_id\n" +
                "AND film_actor.actor_id = actor.actor_id\n" +
                "GROUP BY film_actor.film_id;");
        Query actorIdFilmsQuery = entityManager.createNativeQuery("SELECT actor.actor_id FROM film, actor, film_actor\n" +
                "WHERE film.film_id = film_actor.film_id\n" +
                "AND film_actor.actor_id = actor.actor_id\n" +
                "GROUP BY film_actor.film_id;");


        List<Short> actorIDList = actorIDQuery.getResultList();
        List<String> actorFirstNameList = actorFirstNameQuery.getResultList();
        List<String> actorLastNameList = actorLastNameQuery.getResultList();
        List<Timestamp> actorLastUpdateList = actorLastUpdateQuery.getResultList();
        List<String> actorMoviesList = actorMoviesQuery.getResultList();
        List<Short> actorIdFilmsList = actorIdFilmsQuery.getResultList();
        ObservableList<ActorEntity> olActorFilms = FXCollections.observableArrayList();
        for (int j = 0; j < actorMoviesList.size(); j++){
            Short actorIdFilm = actorIdFilmsList.get(j);
            String actorMovies = actorMoviesList.get(j);
            ActorEntity actorFilm = new ActorEntity(actorIdFilm, actorMovies);
            olActorFilms.add(actorFilm);
        }


        for(int i = 0; i < actorIDList.size(); i++){
            Short actorID = actorIDList.get(i);
            String firstName = actorFirstNameList.get(i);
            String lastName = actorLastNameList.get(i);
            Timestamp lastUpdate = actorLastUpdateList.get(i);
            StringBuilder stringBuilder = new StringBuilder();
          for (ActorEntity actorFilm : olActorFilms){
              if (i == actorFilm.getActorIdFilm()) {
                  stringBuilder.append(actorFilm.getFilmTitles()+", ");
              }
          }
          String actorFilms = stringBuilder.toString();

            ActorEntity actor = new ActorEntity(actorID, firstName, lastName, lastUpdate, actorFilms);
            olActors.add(actor);

        }
    }

    public static void getLanguage(EntityManager entityManager){
        Query languageIDQuery = entityManager.createNativeQuery("SELECT language_id FROM language");
        Query languageNameQuery = entityManager.createNativeQuery("SELECT name FROM language");
        Query languageLastUpdateQuery = entityManager.createNativeQuery("SELECT last_update FROM language");

        List<Byte> languageIDList = languageIDQuery.getResultList();
        List<String> languageNameList = languageNameQuery.getResultList();
        List<Timestamp> languageLastUpdateList = languageLastUpdateQuery.getResultList();

        for(int i = 0; i < languageIDList.size(); i++){
            Byte languageID = languageIDList.get(i);
            String name = languageNameList.get(i);
            Timestamp lastUpdate = languageLastUpdateList.get(i);

            LanguageEntity language = new LanguageEntity(languageID, name, lastUpdate);
            olLanguage.add(language);
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

//    public static void getRentalInfo(EntityManager entityManager) {
//        Query rentalIDQuery = entityManager.createNativeQuery("SELECT rental_id FROM rental");
//        Query rentalDateQuery = entityManager.createNativeQuery("SELECT rental_date FROM rental");
//        Query inventoryIDQuery = entityManager.createNativeQuery("SELECT inventory_id FROM rental");
//        Query customerIDQuery = entityManager.createNativeQuery("SELECT customer_id FROM rental");
//        Query returnDateQuery = entityManager.createNativeQuery("SELECT return_date FROM rental");
//        Query staffIDQuery = entityManager.createNativeQuery("SELECT staff_id FROM rental");
//        Query lastUpdateQuery = entityManager.createNativeQuery("SELECT last_update FROM rental");
//
//        List<Integer> rentalIDList = rentalIDQuery.getResultList();
//        List<Timestamp> rentalDateList = rentalDateQuery.getResultList();
//        List<Integer> rentalInventoryList = inventoryIDQuery.getResultList();
//        List<Short> rentalCustomerIDList = customerIDQuery.getResultList();
//        List<Timestamp> rentalReturnDateList = returnDateQuery.getResultList();
//        List<Byte> rentalStaffIDList = staffIDQuery.getResultList();
//        List<Timestamp> rentallastUpdateList = lastUpdateQuery.getResultList();
//
//        for (int i = 0; i < rentalIDList.size(); i++) {
//            Integer rentalID = rentalIDList.get(i);
//            System.out.println(rentalID);
//            Timestamp rentalDate = rentalDateList.get(i);
//            Integer inventoryID = rentalInventoryList.get(i);
//            Short customerID = rentalCustomerIDList.get(i);
//            Timestamp returnDate = rentalReturnDateList.get(i);
//            Byte staffID = rentalStaffIDList.get(i);
//            Timestamp lastUpdate = rentallastUpdateList.get(i);
//
//            RentalEntity paymentRental = new RentalEntity(rentalID, rentalDate, inventoryID, customerID, returnDate, staffID, lastUpdate);
//            olPaymentRental.add(paymentRental);
//        }
//    }

    public static void getPaymentRentalInfo(EntityManager entityManager){
        Query rentalIDQuery = entityManager.createNativeQuery("SELECT rental.rental_id FROM rental INNER JOIN payment ON payment.rental_id = rental.rental_id ORDER BY payment_id");
        Query paymentIDQuery = entityManager.createNativeQuery("SELECT payment.payment_id FROM payment INNER JOIN rental ON payment.payment_id = rental.rental_id");
        Query rentalDateQuery = entityManager.createNativeQuery("SELECT rental_date FROM rental");
        Query inventoryIDQuery = entityManager.createNativeQuery("SELECT inventory_id FROM rental");
        Query customerIDQuery = entityManager.createNativeQuery("SELECT customer_id FROM rental");
        Query returnDateQuery = entityManager.createNativeQuery("SELECT return_date FROM rental");
        Query staffIDQuery = entityManager.createNativeQuery("SELECT staff_id FROM rental");
        Query amountQuery = entityManager.createNativeQuery("SELECT amount FROM payment");
        Query paymentDateIDQuery = entityManager.createNativeQuery("SELECT payment_date FROM payment");
        Query filmIdQuery = entityManager.createNativeQuery("SELECT film_id FROM rental INNER JOIN payment ON payment.rental_id = rental.rental_id INNER JOIN inventory ON inventory.inventory_id = rental.inventory_id");

        List<Integer> rentalIDList = rentalIDQuery.getResultList();
        List<Short> paymentIDList = paymentIDQuery.getResultList();
        List<Timestamp> rentalDateList = rentalDateQuery.getResultList();
        List<Integer> inventoryIDList = inventoryIDQuery.getResultList();
        List<Short> customerIDList = customerIDQuery.getResultList();
        List<Timestamp> returnDateList = returnDateQuery.getResultList();
        List<Byte> staffIDList = staffIDQuery.getResultList();
        List<BigDecimal> amountList = amountQuery.getResultList();
        List<Timestamp> paymentDateList = paymentDateIDQuery.getResultList();
        List<Short> filmIDList = filmIdQuery.getResultList();

        for(int i = 0; i < rentalIDList.size(); i++){
            Integer rentalId = rentalIDList.get(i);
            Short paymentId = paymentIDList.get(i);
            Timestamp rentalDate = rentalDateList.get(i);
            Integer inventoryId = inventoryIDList.get(i);
            Short customerId = customerIDList.get(i);
            Timestamp returnDate = returnDateList.get(i);
            Byte staffId = staffIDList.get(i);
            BigDecimal amount = amountList.get(i);
            Timestamp paymentDate = paymentDateList.get(i);
            Short filmId = filmIDList.get(i);

            PaymentRentalEntity rental = new PaymentRentalEntity(paymentId, rentalId, rentalDate, inventoryId, customerId, returnDate, staffId, amount, paymentDate, filmId);
            olPaymentRental.add(rental);
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
            getPaymentRentalInfo(entityManager);
            getLanguage(entityManager);
            getHomePageInfo(entityManager);

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
//        TableColumn<BigDecimal, HomePageInfoEntity> col_rentalRate = new TableColumn<>("Rental Rate");
        TableColumn<String, HomePageInfoEntity> col_rating = new TableColumn<>("Rating");
        TableColumn<BigInteger, HomePageInfoEntity> col_totalCopies = new TableColumn<>("Total Copies Available");
        TableColumn<Integer, HomePageInfoEntity> col_availableCopies = new TableColumn<>("Number of Copies Available");
        TableColumn<String, HomePageInfoEntity> col_top10Films = new TableColumn<>("Top 10 of most rented films");

        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_releaseYear.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
        col_language.setCellValueFactory(new PropertyValueFactory<>("language"));
//        col_rentalRate.setCellValueFactory(new PropertyValueFactory<>("rentalRate"));
        col_length.setCellValueFactory(new PropertyValueFactory<>("length"));
        col_rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        col_totalCopies.setCellValueFactory(new PropertyValueFactory<>("totalCopies"));
        col_availableCopies.setCellValueFactory(new PropertyValueFactory<>("availableCopies"));
        col_top10Films.setCellValueFactory(new PropertyValueFactory<>("top10Films"));

        homeTable.getColumns().addAll(col_title, col_releaseYear, col_language /*col_rentalRate*/, col_length, col_rating, col_totalCopies, col_availableCopies);
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
        actorButton.setText("Sk??despelare");
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
            resultBar.setText(sb.toString());
//            resultBar.getItems().add(olFilmId);
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
        comboBox.setPromptText("Film titlar");

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
        TextField actorFilterField = new TextField();

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
        comboBox.setPromptText("Sk??despelare");

        Label customerSearch = new Label();
        customerSearch.setText("Search for actor: ");
        customerSearch.setLayoutX(350);
        customerSearch.setLayoutY(200);
        TextField searchField = new TextField();
        searchField.setPromptText("Search... ");

        VBox vbox = new VBox();

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

        vbox.getChildren().addAll(customerSearch, searchField, actorTable, comboBox, searchButton, actorFilterField, returnToHome);

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
          //TODO: g??r om att ny kund l??ggs till i databasen och tabellen ska uppdateras d??refter!
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
        if((!tfUserName.getText().toString().equals("EFDB1") && !tfPassword.getText().toString().equals("123")) || (!tfUserName.getText().toString().equals("EFDB1") && tfPassword.getText().toString().equals("123")) || (tfUserName.getText().toString().equals("EFDB1") && !tfPassword.getText().toString().equals("123"))){
            lLogInAnswer.setText("Wrong username or password!");
            return false;
        } else return true;
    }
}
