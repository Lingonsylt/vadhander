package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_table")
public class User extends Model {
    @Id
    public int id;

    public String username;
    public String firstname;
    public String lastname;
    public String email;
    public Integer age;

    @OneToMany(cascade=CascadeType.ALL)
    public List<Event> created_events;

    @OneToMany(cascade=CascadeType.ALL)
    public List<Comment> comments;

    @OneToMany(cascade=CascadeType.ALL)
    public List<Attending> attending;

    @ManyToOne(optional = false)
    public List<User> friends;

    public static Finder find() {
        return new Model.Finder(Integer.class, User.class);
    }
}
