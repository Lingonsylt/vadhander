package models;

import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Tag extends Model {
    @Id
    public String text;

    @ManyToOne(optional = false)
    public Event event;

    public static Finder find() {
        return new Model.Finder(Integer.class, Tag.class);
    }
}
