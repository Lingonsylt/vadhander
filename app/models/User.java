package models;

import play.Logger;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "user_table")
public class User extends Model {
    @Id
    public int id;

    public String name;
    public String email;

    public Set<String> subscription;

    public User() {
        subscription = new HashSet<String>();
    }

    public void addSub(String sub) {
        subscription.add(sub);
    }

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

    public List<String> getFeedTags(){
        return new ArrayList<String>(subscription);
    }
}
