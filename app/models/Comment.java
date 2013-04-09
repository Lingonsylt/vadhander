package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Comment extends Model {
    @Id
    public int id;

    public User user;

    @ManyToOne
    public Event event;

    public String text;
}
