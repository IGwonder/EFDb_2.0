package Entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "language", schema = "sakila", catalog = "")
public class LanguageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id")
    private Short language_id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_update")
    private Timestamp last_update;

    public LanguageEntity() {
    }

    public LanguageEntity(Short language_id, String name, Timestamp last_update) {
        this.language_id = language_id;
        this.name = name;
        this.last_update = last_update;
    }

    public Short getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(Short language_id) {
        this.language_id = language_id;
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
