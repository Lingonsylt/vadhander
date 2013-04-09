package models;

import org.joda.time.DateTime;
import play.db.ebean.Model;
/*import com.vividsolutions.jts.geom.Point;
import org.hibernate.annotations.Type;*/

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Event extends Model {
    @Id
    public int id;

    public String caption;

    public List<String> tags;

    /*@Type(type="org.hibernate.spatial.GeometryType")
    @Column(columnDefinition="Point")
    public Point location;           */

    public DateTime time_created;

    public User creator;

    @OneToMany
    public List<Attending> attending;

    @OneToMany
    public List<Comment> comments;

}
