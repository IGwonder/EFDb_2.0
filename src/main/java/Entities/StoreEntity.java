package Entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "store", schema = "sakila", catalog = "")
public class StoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Short store_id;

    @Column(name = "manager'_staff_id")
    private Short manager_staff_id;

    @Column(name = "address_id")
    private Short address_id;

    @Column(name = "last_update")
    private Timestamp last_update;

    public StoreEntity() {
    }

    public StoreEntity(Short store_id, Short manager_staff_id, Short address_id, Timestamp last_update) {
        this.store_id = store_id;
        this.manager_staff_id = manager_staff_id;
        this.address_id = address_id;
        this.last_update = last_update;
    }

    public Short getStore_id() {
        return store_id;
    }

    public void setStore_id(Short store_id) {
        this.store_id = store_id;
    }

    public Short getManager_staff_id() {
        return manager_staff_id;
    }

    public void setManager_staff_id(Short manager_staff_id) {
        this.manager_staff_id = manager_staff_id;
    }

    public Short getAddress_id() {
        return address_id;
    }

    public void setAddress_id(Short address_id) {
        this.address_id = address_id;
    }

    public Timestamp getLast_update() {
        return last_update;
    }

    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }
}
