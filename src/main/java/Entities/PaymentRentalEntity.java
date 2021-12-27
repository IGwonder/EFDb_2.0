package Entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
public class PaymentRentalEntity {

    private Short paymentId;

    private Short customerId;

    private Byte staffId;

    private Integer rentalId;

    private Timestamp returnDate;

    private Integer inventoryId;

    private Timestamp rentalDate;

    private BigDecimal amount;

    private Timestamp paymentDate;

    private Timestamp lastUpdate;

    private Short filmId;


    public PaymentRentalEntity(Short paymentId, Integer rentalId, Timestamp rentalDate, Integer inventoryId, Short customerId, Timestamp returnDate, Byte staffId, BigDecimal amount, Timestamp paymentDate, Short filmId) {
        this.paymentId = paymentId;
        this.customerId = customerId;
        this.staffId = staffId;
        this.rentalId = rentalId;
        this.returnDate = returnDate;
        this.inventoryId = inventoryId;
        this.rentalDate = rentalDate;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.filmId = filmId;
    }

    public Short getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Short paymentId) {
        this.paymentId = paymentId;
    }

    public Short getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Short customerId) {
        this.customerId = customerId;
    }

    public Byte getStaffId() {
        return staffId;
    }

    public void setStaffId(Byte staffId) {
        this.staffId = staffId;
    }

    public Integer getRentalId() {
        return rentalId;
    }

    public void setRentalId(Integer rentalId) {

        this.rentalId = rentalId;
    }

    public Timestamp getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Timestamp returnDate) {
        this.returnDate = returnDate;
    }

    public Integer getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Timestamp getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Timestamp rentalDate) {
        this.rentalDate = rentalDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Short getFilmId() {
        return filmId;
    }

    public void setFilmId(Short filmId) {
        this.filmId = filmId;
    }

    public PaymentRentalEntity(Short paymentId, Short customerId, Byte staffId, Integer rentalId, Timestamp returnDate, Integer inventoryId, Timestamp rentalDate, BigDecimal amount, Timestamp paymentDate, Timestamp lastUpdate, Short filmId) {
        this.paymentId = paymentId;
        this.customerId = customerId;
        this.staffId = staffId;
        this.rentalId = rentalId;
        this.returnDate = returnDate;
        this.inventoryId = inventoryId;
        this.rentalDate = rentalDate;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.lastUpdate = lastUpdate;
        this.filmId = filmId;

    }
}