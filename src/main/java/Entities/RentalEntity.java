package Entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;


    @Entity
    @Table(name = "rental", schema = "sakila", catalog = "")
    public class RentalEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
@Id
@Column(name = "rental_id")
private Integer rentalId;

    @Column(name = "rental_date")
    private Timestamp rentalDate;

    @Column(name = "inventory_id")
    private Integer inventoryId;

    @Column(name = "customer_id")
    private Short customerId;

    @Column(name = "return_date")
    private Timestamp returnDate;

    @Column(name = "staff_id")
    private Byte staffId;

    @Column(name = "last_update")
    private Timestamp lastUpdate;

    public Integer getRentalId() {
        return rentalId;
    }

    public void setRentalId(Integer rentalId) {
        this.rentalId = rentalId;
    }

    public Timestamp getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Timestamp rentalDate) {
        this.rentalDate = rentalDate;
    }

    public Integer getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Short getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Short customerId) {
        this.customerId = customerId;
    }

    public Timestamp getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Timestamp returnDate) {
        this.returnDate = returnDate;
    }

    public Byte getStaffId() {
        return staffId;
    }

    public void setStaffId(Byte staffId) {
        this.staffId = staffId;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    public RentalEntity(Integer rentalId, Timestamp rentalDate, Integer inventoryId, Short customerId, Timestamp returnDate, Byte staffId, Timestamp lastUpdate) {
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
