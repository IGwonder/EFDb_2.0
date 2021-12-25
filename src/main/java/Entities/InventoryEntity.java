package Entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "inventory", schema = "sakila",catalog = "")
public class InventoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Short inventory_id;

    @Column(name = "film_id")
    private Short film_id;

    @Column(name = "store_id")
    private Short store_id;

    @Column(name = "last_update")
    private Timestamp last_update;

    public InventoryEntity() {
    }

    public InventoryEntity(Short inventory_id, Short film_id, Short store_id, Timestamp last_update) {
        this.inventory_id = inventory_id;
        this.film_id = film_id;
        this.store_id = store_id;
        this.last_update = last_update;
    }

    public Short getInventory_id() {
        return inventory_id;
    }

    public void setInventory_id(Short inventory_id) {
        this.inventory_id = inventory_id;
    }

    public Short getFilm_id() {
        return film_id;
    }

    public void setFilm_id(Short film_id) {
        this.film_id = film_id;
    }

    public Short getStore_id() {
        return store_id;
    }

    public void setStore_id(Short store_id) {
        this.store_id = store_id;
    }

    public Timestamp getLast_update() {
        return last_update;
    }

    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }
}
