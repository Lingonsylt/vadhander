package models;

import play.Logger;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_table")
public class User extends Model {
    @Id
    public int id;

    public String name;
    public String email;

    @OneToMany
    public List<Event> created_events;

    @OneToMany
    public List<Comment> comments;

    @OneToMany(cascade=CascadeType.ALL)
    public List<Attending> attending;

    @ManyToOne(optional = false)
    public List<User> friends;

    public static Finder find() {
        return new Model.Finder(Integer.class, User.class);
    }

    public String getFullName() {
        Logger.error("User.getFullName() is a stub!");
        return "Fakefirstname Fakelastname";
    }
}
