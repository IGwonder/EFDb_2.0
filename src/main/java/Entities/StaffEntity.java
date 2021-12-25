package Entities;

import com.mysql.cj.jdbc.Blob;

import javax.persistence.*;

@Entity
@Table(name = "staff", schema = "sakila", catalog = "")
public class StaffEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private Short staff_id;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "address_id")
    private Short address_id;

    //@Column(name = "picture")
    //private Blob picture;

    @Column(name = "email")
    private String email;

    @Column(name = "store_id")
    private Short store_id;

    @Column(name = "active")
    private Short active;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    public StaffEntity() {
    }

    public StaffEntity(Short staff_id, String first_name, String last_name, Short address_id, Blob picture, String email, Short store_id, Short active, String username, String password) {
        this.staff_id = staff_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address_id = address_id;
        //this.picture = picture;
        this.email = email;
        this.store_id = store_id;
        this.active = active;
        this.username = username;
        this.password = password;
    }

    public Short getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(Short staff_id) {
        this.staff_id = staff_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Short getAddress_id() {
        return address_id;
    }

    public void setAddress_id(Short address_id) {
        this.address_id = address_id;
    }
/*
    public Blob getPicture() {
        return picture;
    }

    public void setPicture(Blob picture) {
        this.picture = picture;
    }
*/

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Short getStore_id() {
        return store_id;
    }

    public void setStore_id(Short store_id) {
        this.store_id = store_id;
    }

    public Short getActive() {
        return active;
    }

    public void setActive(Short active) {
        this.active = active;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
