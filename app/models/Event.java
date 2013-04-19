package models;

import com.avaje.ebean.*;
import org.joda.time.DateTime;
import play.Logger;

import play.db.ebean.Model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Event extends Model {
    @Id
    public int id;

    public String caption;

    public List<Tag> tags = new ArrayList<>();

    private void updateCoord() {
        if (latitude == 0 || longitude == 0) {
            throw new RuntimeException("latitude and longitude must not be 0!");
        }

        try {
            String s = "UPDATE event SET coord = ST_SetSRID(ST_MakePoint(latitude, longitude), 3006) WHERE id = :id";
            SqlUpdate update = Ebean.createSqlUpdate(s);
            update.setParameter("id", id);


            int modifiedCount = Ebean.execute(update);
            if (modifiedCount != 1) {
                Logger.error("Cannot update coordinate! Is PostGIS installed?");
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

    /*@Override
    public void refresh() {
        super.refresh();
        updateCoord();
    }*/

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



    public List<Event> getCloseEvents(int count) {
        String sql = "SELECT " +
        "g2.id AS id, g2.caption As caption, g2.latitude as latitude, g2.longitude AS longitude, g2.time_created AS time_created, g2.creator_id AS creator_id, ST_Distance(g1.coord, g2.coord) AS distance " +
        "FROM event As g1, event As g2 " +
        "WHERE g1.id = :id and g1.id <> g2.id " +
        "ORDER BY distance " +
        "LIMIT :count;";

        RawSql rawSql = RawSqlBuilder.unparsed(sql)
                .columnMapping("id",  "id")
                .columnMapping("caption",  "caption")
                .columnMapping("latitude",  "latitude")
                .columnMapping("longitude",  "longitude")
                .columnMapping("time_created",  "time_created")
                .columnMapping("creator_id",  "creator.id")
                .columnMapping("distance",  "distance")
                .create();
        com.avaje.ebean.Query<Event> query = Ebean.find(Event.class);
        query.setParameter("id", id);
        query.setParameter("count", count);
        query.setRawSql(rawSql);
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
