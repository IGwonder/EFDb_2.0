package Entities;


import javax.persistence.*;

@Entity
@Table(name = "film_text", schema = "sakila", catalog = "")
public class FilmTextEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private Short film_id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    public FilmTextEntity() {
    }

    public FilmTextEntity(Short film_id, String title, String description) {
        this.film_id = film_id;
        this.title = title;
        this.description = description;
    }

    public Short getFilm_id() {
        return film_id;
    }

    public void setFilm_id(Short film_id) {
        this.film_id = film_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
