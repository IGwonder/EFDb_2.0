package Entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "film_category", schema = "sakila", catalog = "")
public class FilmCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private Short film_id;

    @Column(name = "category_id")
    private Short category_id;

    @Column(name = "last_update")
    private Timestamp last_update;

    public FilmCategoryEntity() {
    }

    public FilmCategoryEntity(Short film_id, Short category_id, Timestamp last_update) {
        this.film_id = film_id;
        this.category_id = category_id;
        this.last_update = last_update;
    }

    public Short getFilm_id() {
        return film_id;
    }

    public void setFilm_id(Short film_id) {
        this.film_id = film_id;
    }

    public Short getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Short category_id) {
        this.category_id = category_id;
    }

    public Timestamp getLast_update() {
        return last_update;
    }

    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }
}