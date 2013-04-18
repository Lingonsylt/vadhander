package models;

import org.joda.time.DateTime;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
//@IdClass(EventUser.class)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "event_id", "user_id" }))
public class Attending extends Model {
    @Id
    int id;
    /*@EmbeddedId
    public EventUser eventuser;


    public EventUser getEventUser() {
        return eventuser;
    }*/

    //@Id
    //@JoinColumn(name="user_id")
    @ManyToOne(optional = false)
    public User user;

    //@Id
    //@JoinColumn(name="event_id")
    @ManyToOne(optional = false)
    public Event event;

    //@Column(name="event_id")
    /*@MapsId("event_id")
    @ManyToOne
    public Event event;


    @MapsId("user_id")
    @ManyToOne
    public User user;
    */

    public void setEventUser(Event event, User user) {
        /*if (this.eventuser == null) {
            this.eventuser = new EventUser(event, user);
            //this.eventuser = new EventUser(event.id, user.id);
        } else {
            //this.eventuser.event = event;
            //this.eventuser.user = user;

            //this.eventuser.event_id = event.id;
            //this.eventuser.user_id = user.id;
        } */
    }





    public String state;

    public DateTime timestamp;

    public static Finder find() {
        return new Model.Finder(EventUser.class, Attending.class);
    }

}
