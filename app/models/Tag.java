package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tag extends Model {
    @Id
    public String text;

    @ManyToMany
    public List<Event> event;
    public Tag(){
        event = new ArrayList<Event>();
    }

    public static Finder find() {
        return new Model.Finder(Integer.class, Tag.class);
    }
}
