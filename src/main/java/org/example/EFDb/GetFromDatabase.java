package org.example.EFDb;

import Entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.example.EFDb.EFDb.*;

public class GetFromDatabase {

    public GetFromDatabase() {
    }

    public static void FilmTitles(EntityManager entityManager){
        Query query = entityManager.createNativeQuery("SELECT title FROM film");
        List<String> films = query.getResultList();
        for (String title : films){
            olFilmTitles.add(title);
        }
    }

    public static void Films(EntityManager entityManager){
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

    public static void HomePageInfo(EntityManager entityManager){
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

    public static void ActorNames(EntityManager entityManager){

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

    public static void Actors(EntityManager entityManager){
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

    public static void Language(EntityManager entityManager){
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

    public static void Customers(EntityManager entityManager){
        Query customerIDQuery = entityManager.createNativeQuery("SELECT customer_id from customer ORDER BY customer_id;");
        Query storeIDQuery = entityManager.createNativeQuery("SELECT store_id FROM customer ORDER BY customer_id");
        Query customerFirstNameQuery = entityManager.createNativeQuery("SELECT first_name FROM customer ORDER BY customer_id");
        Query customerLastNameQuery = entityManager.createNativeQuery("SELECT last_name FROM customer ORDER BY customer_id");
        Query emailQuery = entityManager.createNativeQuery("SELECT email FROM customer ORDER BY customer_id");
        Query addressIDQuery = entityManager.createNativeQuery("SELECT address_id FROM customer ORDER BY customer_id");
        Query activeQuery = entityManager.createNativeQuery("SELECT active FROM customer ORDER BY customer_id");
        Query createDateQuery = entityManager.createNativeQuery("SELECT create_date FROM customer ORDER BY customer_id");
        Query lastUpdateQuery = entityManager.createNativeQuery("SELECT last_update FROM customer ORDER BY customer_id");

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
            if (active) olCustomer.add(customer);

        }

    }

    public static void PaymentRentalInfo(EntityManager entityManager){
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
}
