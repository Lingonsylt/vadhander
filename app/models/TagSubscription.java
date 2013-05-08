package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TagSubscription extends Model {
    @Id
    public int id;

    @ManyToOne
    public User user;

    public String tagText;

    public static Finder find() {
        return new Finder(Integer.class, TagSubscription.class);
    }
}
