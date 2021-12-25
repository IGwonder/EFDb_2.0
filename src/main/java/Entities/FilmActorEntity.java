package Entities;

import javax.persistence.*;

@Entity
@Table(name = "film_actor", schema = "sakila", catalog = "")
public class FilmActorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private Short film_id;

    @Column(name = "category_id")
    private Short category_id;

    @Column(name = "last_update")
    private Short last_update;

    public FilmActorEntity() {
    }

    public FilmActorEntity(Short film_id, Short category_id, Short last_update) {
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

    public Short getLast_update() {
        return last_update;
    }

    public void setLast_update(Short last_update) {
        this.last_update = last_update;
    }
}
