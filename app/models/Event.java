package models;

import com.avaje.ebean.*;
import org.joda.time.DateTime;
import play.Logger;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Event extends Model {
    @Id
    public int id;

    public String caption;

    @OneToMany
    public List<Tag> tags = new ArrayList<>();

    /**
     * Update the PostGIS coord column for this Event-object in the database based on its latitude and longitude
     */
    private void updateCoord() {
        // Validate that the latitude and longitude are set. TODO: Is there a better JPA-way to do this?
        if (latitude == 0 || longitude == 0) {
            throw new RuntimeException("latitude or longitude must not be 0!");
        }

        try {
            // Update the column coord in the database, based on the database columns latitude and longitude
            String s = "UPDATE event SET coord = ST_SetSRID(ST_MakePoint(latitude, longitude), 3006) WHERE id = :id";
            SqlUpdate update = Ebean.createSqlUpdate(s);
            update.setParameter("id", id);

            int modifiedCount = Ebean.execute(update);
            if (modifiedCount != 1) {
                Logger.error("Cannot update coordinate! Is PostGIS installed? (" + modifiedCount + " modified)");
            }
        } catch (RuntimeException ex) {
            Logger.error("Cannot update coordinate! Is PostGIS installed?");
        }
    }

    @Override
    public void save() {
        super.save();
        updateCoord();
    }

    @Override
    public String toString() {
        return "Event(id=" + id + ")";
    }

    @Column(nullable = false)
    public float latitude = 0;

    @Column(nullable = false)
    public float longitude = 0;

    @Transient
    public float distance;

    /**
     * Query the database for the count closest to this event, ordered by distance.
     * @param count The max number of events to return
     */
    public List<Event> getCloseEvents(int count) {

        // Select :count events, ordered by distance, containing the extra column "distance" showing the distance between this event and the other event
        String sql = "SELECT " +
        "g2.id AS id, g2.caption As caption, g2.latitude as latitude, g2.longitude AS longitude, g2.time_created AS time_created, g2.creator_id AS creator_id, ST_Distance(g1.coord, g2.coord) AS distance " +
        "FROM event As g1, event As g2 " +
        "WHERE g1.id = :id and g1.id <> g2.id " +
        "ORDER BY distance " +
        "LIMIT :count;";

        // Map all columns from the result to properties on Event.class
        RawSql rawSql = RawSqlBuilder.unparsed(sql)
                .columnMapping("id",  "id")
                .columnMapping("caption",  "caption")
                .columnMapping("latitude",  "latitude")
                .columnMapping("longitude",  "longitude")
                .columnMapping("time_created",  "time_created")
                .columnMapping("creator_id",  "creator.id")
                .columnMapping("distance",  "distance")
                .create();

        // Bind parameters and execute the query
        com.avaje.ebean.Query<Event> query = Ebean.find(Event.class);
        query.setParameter("id", id);
        query.setParameter("count", count);
        query.setRawSql(rawSql);

        // Return the result of the query as a list of Event-objects with the .distance property set
        return query.findList();
    }

    public DateTime time_created;

    @ManyToOne(optional = false)
    public User creator;

    @OneToMany(cascade=CascadeType.ALL)
    public List<Attending> attending;

    @OneToMany
    public List<Comment> comments;

    public static Finder find() {
        return new Model.Finder(Integer.class, Event.class);
    }


}
