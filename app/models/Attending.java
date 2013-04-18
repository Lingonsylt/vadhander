package models;

import org.joda.time.DateTime;
import play.db.ebean.Model;
import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "event_id", "user_id" }))
public class Attending extends Model {
    @Id
    int id;

    @ManyToOne(optional = false)
    public User user;

    @ManyToOne(optional = false)
    public Event event;

    public String state;

    public DateTime timestamp;

    public static Finder find() {
        return new Model.Finder(Integer.class, Attending.class);
    }

}
