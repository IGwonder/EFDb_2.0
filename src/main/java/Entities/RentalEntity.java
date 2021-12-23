package Entities;


import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table
public class RentalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    private Short rental_id;

    @Column(name = "rental_date")
    private Timestamp rental_date;

    @Column(name = "inventory_id")
    private Short inventory_id;

    @Column(name = "customer_id")
    private Short customer_id;

    @Column(name = "return_date")
    private Timestamp return_date;

    @Column(name = "staff_id")
    private Short staff_id;

    @Column(name = "last_update")
    private Timestamp last_update;

    public RentalEntity() {
    }

    public RentalEntity(Short rental_id, Timestamp rental_date, Short inventory_id, Short customer_id, Timestamp return_date, Short staff_id, Timestamp last_update) {
        this.rental_id = rental_id;
        this.rental_date = rental_date;
        this.inventory_id = inventory_id;
        this.customer_id = customer_id;
        this.return_date = return_date;
        this.staff_id = staff_id;
        this.last_update = last_update;
    }

    public Short getRental_id() {
        return rental_id;
    }

    public void setRental_id(Short rental_id) {
        this.rental_id = rental_id;
    }

    public Timestamp getRental_date() {
        return rental_date;
    }

    public void setRental_date(Timestamp rental_date) {
        this.rental_date = rental_date;
    }

    public Short getInventory_id() {
        return inventory_id;
    }

    public void setInventory_id(Short inventory_id) {
        this.inventory_id = inventory_id;
    }

    public Short getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Short customer_id) {
        this.customer_id = customer_id;
    }

    public Timestamp getReturn_date() {
        return return_date;
    }

    public void setReturn_date(Timestamp return_date) {
        this.return_date = return_date;
    }

    public Short getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(Short staff_id) {
        this.staff_id = staff_id;
    }

    public Timestamp getLast_update() {
        return last_update;
    }

    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }
}

