package Entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "category", schema = "sakila", catalog = "")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Short category_id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_update")
    private Timestamp last_update;

    public CategoryEntity() {
    }

    public CategoryEntity(Short category_id, String name, Timestamp last_update) {
        this.category_id = category_id;
        this.name = name;
        this.last_update = last_update;
    }

    public Short getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Short category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getLast_update() {
        return last_update;
    }

    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }
}
