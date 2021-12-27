package Entities;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;

public class HomePageInfoEntity {

    private String title;
    private Date releaseYear;
    private String language;
    private BigDecimal rentalRate;
    private Short length;
    private String rating;
    private BigInteger totalCopies;
    private Short missingCopyFilmID;
    private BigInteger numberOfMissingCopies;
    private int availableCopies;
    private String top10Films;

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public Short getMissingCopyFilmID() {
        return missingCopyFilmID;
    }

    public void setMissingCopyFilmID(Short missingCopyFilmID) {
        this.missingCopyFilmID = missingCopyFilmID;
    }

    public BigInteger getNumberOfMissingCopies() {
        return numberOfMissingCopies;
    }

    public void setNumberOfMissingCopies(BigInteger numberOfMissingCopies) {
        this.numberOfMissingCopies = numberOfMissingCopies;
    }

    public String getTop10Films() {
        return top10Films;
    }

    public void setTop10Films(String top10Films) {
        this.top10Films = top10Films;
    }

    public BigInteger getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(BigInteger totalCopies) {
        this.totalCopies = totalCopies;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Date releaseYear) {
        this.releaseYear = releaseYear;
    }

    public BigDecimal getRentalRate() {
        return rentalRate;
    }

    public void setRentalRate(BigDecimal rentalRate) {
        this.rentalRate = rentalRate;
    }

    public Short getLength() {
        return length;
    }

    public void setLength(Short length) {
        this.length = length;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public HomePageInfoEntity(String title, Date releaseYear, String languageName, BigDecimal rentalRate, Short length, String rating, BigInteger totalCopies, int availableCopies) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.language = languageName;
        this.rentalRate = rentalRate;
        this.length = length;
        this.rating = rating;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;

    }

    public HomePageInfoEntity(Short missingCopyFilmID, BigInteger numberOfMissingCopies) {
        this.missingCopyFilmID = missingCopyFilmID;
        this.numberOfMissingCopies = numberOfMissingCopies;
    }

    public HomePageInfoEntity(String top10Film) {
        this.top10Films = top10Film;
    }

    public HomePageInfoEntity() {
    }
}
