package Entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "language", schema = "sakila", catalog = "")
public class LanguageEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "language_id")
    private Byte languageId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "last_update")
    private Timestamp lastUpdate;

    public LanguageEntity(Byte languageID, String name, Timestamp lastUpdate) {
        this.languageId = languageID;
        this.name = name;
        this.lastUpdate = lastUpdate;
    }

    public Byte getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Byte languageId) {
        this.languageId = languageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LanguageEntity that = (LanguageEntity) o;
        return Objects.equals(languageId, that.languageId) && Objects.equals(name, that.name) && Objects.equals(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(languageId, name, lastUpdate);
    }
}
