package models;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

//@Embeddable
public class EventUser implements Serializable {

    //@Column(name = "user_id", nullable = false)
    public int user;

    //@Column(name = "event_id", nullable = false)
    public int event;

    /*public int event_id;
    public int user_id;*/

    /*public EventUser() {

    }

    public EventUser(Event event, User user) {
        //this.user = user;
        //this.event = event;
    } */

    /*public EventUser(int event_id, int user_id) {
        this.user_id = user_id;
        this.event_id = event_id;
    } */

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EventUser other = (EventUser) obj;
        /*if (!(this.user.id == other.user.id)) {
            return false;
        }
        if (!(this.event.id == other.event.id)) {
            return false;
        } */
        return true;
    }

    @Override
    public int hashCode() {
        // TODO: Will this work?
        return 0;
        //return this.user.id * 17 + this.event.id * 23;
    }
}