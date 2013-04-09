package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_table")
public class User extends Model {
    @Id
    public int id;

    public String name;
    public String email;

    @OneToMany
    public List<Attending> attending;

    @ManyToOne
    public List<User> friends;
}
