package models;

import play.db.ebean.Model;

import javax.persistence.*;


@Entity
public class Attending extends Model {
    @Embeddable
    public class EventUser {
        public int event_id;
        public int user_id;

        public EventUser(int event_id, int user_id) {
            this.user_id = user_id;
            this.event_id = event_id;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final EventUser other = (EventUser) obj;
            if (!(this.user_id == other.user_id)) {
                return false;
            }
            if (!(this.event_id == other.event_id)) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            // TODO: Will this work?
            return this.user_id * 17 + this.event_id * 23;
        }
    }

    @EmbeddedId
    public EventUser eventuser;


    //@Column(name="event_id")
    @MapsId("event_id")
    @ManyToOne
    public Event event;

    @MapsId("user_id")
    @ManyToOne
    public User user;

    public String state;


}
