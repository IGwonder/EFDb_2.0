package Entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "payment", schema = "sakila", catalog = "")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Short payment_id;

    @Column(name = "customer_id")
    private Short customer_id;

    @Column(name = "staff_id")
    private Short staff_id;

    @Column(name = "rental_id")
    private Short rental_id;

    @Column(name = "amount")
    private double amount;

    @Column(name = "payment_date")
    private Timestamp payment_day;

    public PaymentEntity() {
    }

    public PaymentEntity(Short payment_id, Short customer_id, Short staff_id, Short rental_id, double amount, Timestamp payment_day) {
        this.payment_id = payment_id;
        this.customer_id = customer_id;
        this.staff_id = staff_id;
        this.rental_id = rental_id;
        this.amount = amount;
        this.payment_day = payment_day;
    }

    public Short getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(Short payment_id) {
        this.payment_id = payment_id;
    }

    public Short getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Short customer_id) {
        this.customer_id = customer_id;
    }

    public Short getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(Short staff_id) {
        this.staff_id = staff_id;
    }

    public Short getRental_id() {
        return rental_id;
    }

    public void setRental_id(Short rental_id) {
        this.rental_id = rental_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Timestamp getPayment_day() {
        return payment_day;
    }

    public void setPayment_day(Timestamp payment_day) {
        this.payment_day = payment_day;
    }
}
