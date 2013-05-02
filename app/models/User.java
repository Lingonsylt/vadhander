package models;

import play.Logger;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

import play.data.Form;

import play.data.validation.Constraints.Required;
import play.data.validation.Constraints.*;

@Entity
@Table(name = "user_table")
public class User extends Model {
    @Id
    public int id;

    @Required public String username;
    @Required public String firstname;
    @Required public String lastname;
    @Required @Min(1900) @Max(2100) public int birthyear;
    @Required @Email public String email;
    @Required public String password;

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

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public int getBirthyear() {
        return birthyear;
    }

    public String getPassword() {
        return password;
    }

}
