package models;

import play.db.ebean.Model;

import javax.persistence.*;

@Entity
public class Comment extends Model {
    @Id
    public int id;

    @ManyToOne(optional = false)
    public User user;

    @ManyToOne(optional = false)
    public Event event;

    public String text;

    public static Finder find() {
        return new Model.Finder(Integer.class, Comment.class);
    }

}
