package Entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "customer", schema = "sakila", catalog = "")
public class CustomerEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "customer_id")
    private Short customerId;
    @Basic
    @Column(name = "store_id")
    private Byte storeId;
    @Basic
    @Column(name = "first_name")
    private String firstName;
    @Basic
    @Column(name = "last_name")
    private String lastName;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "address_id")
    private Short addressId;
    @Basic
    @Column(name = "active")
    private Boolean active;
    @Basic
    @Column(name = "create_date")
    private Timestamp createDate;
    @Basic
    @Column(name = "last_update")
    private Timestamp lastUpdate;

    public CustomerEntity(Short customerID, Byte storeID, String customerFirstName, String customerLastName, String email, String customerRental, Boolean active, Timestamp dateList, Timestamp lastUpdate) {
    }

    public CustomerEntity(Short customerID, Short storeID, String customerFirstName, String customerLastName, String email, String country, String city, String address, Short addressID, String customerRental, Boolean active, Timestamp dateList, Timestamp lastUpdate) {
    }

    public CustomerEntity(String country, String city, String customerRental) {
    }

    public CustomerEntity() {

    }






    public Short getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Short customerId) {
        this.customerId = customerId;
    }

    public Byte getStoreId() {
        return storeId;
    }

    public void setStoreId(Byte storeId) {
        this.storeId = storeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Short getAddressId() {
        return addressId;
    }

    public void setAddressId(Short addressId) {
        this.addressId = addressId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public CustomerEntity(Short customerID, Short storeID, String customerFirstName, String customerLastName, String email, String address, Short addressID, Boolean active, Timestamp dateList, Timestamp lastUpdate) {
    }

    public CustomerEntity(Byte storeID, String firstName, String lastName, String email, Short addressID, Boolean active, Timestamp createDate, Timestamp lastUpdate) {
        this.storeId = storeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.addressId = addressID;
        this.active = active;
        this.createDate = createDate;
        this.lastUpdate = createDate;
    }

    public CustomerEntity(Short customerId, Byte storeId, String firstName, String lastName, String email, Short addressId, Boolean active, Timestamp createDate, Timestamp lastUpdate) {
        this.customerId = customerId;
        this.storeId = storeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.addressId = addressId;
        this.active = active;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerEntity that = (CustomerEntity) o;
        return Objects.equals(customerId, that.customerId) && Objects.equals(storeId, that.storeId) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(email, that.email) && Objects.equals(addressId, that.addressId) && Objects.equals(active, that.active) && Objects.equals(createDate, that.createDate) && Objects.equals(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, storeId, firstName, lastName, email, addressId, active, createDate, lastUpdate);
    }
}
