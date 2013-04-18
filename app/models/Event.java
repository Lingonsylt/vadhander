package models;

import org.joda.time.DateTime;
import play.db.ebean.Model;
/*import com.vividsolutions.jts.geom.Point;
import org.hibernate.annotations.Type;*/

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Event extends Model {
    @Id
    public int id;

    public String caption;

    //@ElementCollection
    //@OneToMany(cascade = CascadeType.ALL)
    public List<Tag> tags = new ArrayList<>();

    //@ElementCollection
    //public ArrayList<String> tags;

    //@ElementCollection
    //public ArrayList<String> tags = new ArrayList<String>();

    //public String taggs;

    /*@Type(type="org.hibernate.spatial.GeometryType")
    @Column(columnDefinition="Point")
    public Point location;           */

    public DateTime time_created;

    @ManyToOne(optional = false)
    public User creator;

    @OneToMany(cascade=CascadeType.ALL)
    public List<Attending> attending; // = new ArrayList<>();

    @OneToMany
    public List<Comment> comments;

    public static Finder find() {
        return new Model.Finder(Integer.class, Event.class);
    }


}
