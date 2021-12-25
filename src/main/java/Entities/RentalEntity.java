package Entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

public class RentalEntity {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
@Id
@Column(name = "rental_id")
private Short rentalId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private Date rentalDate;

    @Column(name = "inventory_id")
    private Short inventoryId;

    @Column(name = "customer_id")
    private Short customerId;

    @Column(name = "return_date")
    private Date returnDate;

    @Column(name = "staff_id")
    private Short staffId;

    @Column(name = "last_update")
    private Date lastUpdate;

    public Short getRentalId() {
        return rentalId;
    }

    public void setRentalId(Short rentalId) {
        this.rentalId = rentalId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    public Short getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Short inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Short getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Short customerId) {
        this.customerId = customerId;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Short getStaffId() {
        return staffId;
    }

    public void setStaffId(Short staffId) {
        this.staffId = staffId;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    public RentalEntity(Short rentalId, Date rentalDate, Short inventoryId, Short customerId, Date returnDate, Short staffId, Date lastUpdate) {
        this.rentalId = rentalId;
        this.rentalDate = rentalDate;
        this.inventoryId = inventoryId;
        this.customerId = customerId;
        this.returnDate = returnDate;
        this.staffId = staffId;
        this.lastUpdate = lastUpdate;
    }

    public RentalEntity() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentalEntity that = (RentalEntity) o;
        return Objects.equals(rentalId, that.rentalId)  && Objects.equals(rentalDate, that.rentalDate) && Objects.equals(inventoryId, that.inventoryId) && Objects.equals(customerId, that.customerId) && Objects.equals(returnDate, that.returnDate) && Objects.equals(staffId, that.staffId) && Objects.equals(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rentalId, rentalDate, inventoryId, customerId, returnDate, staffId, lastUpdate);
    }


}
