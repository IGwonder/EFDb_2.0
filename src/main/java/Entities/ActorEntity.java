package Entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "actor", schema = "sakila", catalog = "")
public class ActorEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "actor_id")
    private Short actorId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "last_update")
    private Timestamp lastUpdate;

    private Short actorIdFilm;

    private String filmTitles;

    public String getActorFilms() {
        return actorFilms;
    }

    public void setActorFilms(String actorFilms) {
        this.actorFilms = actorFilms;
    }

    private String actorFilms;

    public Short getActorIdFilm() {
        return actorIdFilm;
    }

    public void setActorIdFilm(Short actorIdFilm) {
        this.actorIdFilm = actorIdFilm;
    }

    public String getFilmTitles() {
        return filmTitles;
    }

    public void setFilmTitles(String filmTitles) {
        this.filmTitles = filmTitles;
    }



    public Short getActorId() {
        return actorId;
    }

    public void setActorId(Short actorId) {
        this.actorId = actorId;
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

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public ActorEntity(Short actorId, String firstName, String lastName, Timestamp lastUpdate, String actorFilms) {
        this.actorId = actorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.lastUpdate = lastUpdate;
        this.actorFilms = actorFilms;
    }

    public ActorEntity(Short actorIdFilm, String filmTitles) {
        this.actorIdFilm = actorIdFilm;
        this.filmTitles = filmTitles;
    }

    public ActorEntity() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActorEntity that = (ActorEntity) o;
        return Objects.equals(actorId, that.actorId) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actorId, firstName, lastName, lastUpdate);
    }
}
