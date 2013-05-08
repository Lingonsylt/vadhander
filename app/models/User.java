package models;

import play.Logger;
import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Max;
import play.data.validation.Constraints.Min;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user_table")
public class User extends Model {
    @Id
    public int id;

    @Required public String username;
    @Required public String firstname;
    @Required public String lastname;
    @Required @Min(1900) @Max(2100) public int birthyear;
    @Required @Email public String email;
    @Required public String password;

    public Set<String> subscription;

    public User() {
        subscription = new HashSet<String>();
    }

    @OneToMany
    public List<Event> created_events;

    @OneToMany
    public List<Comment> comments;

    @OneToMany
    public List<TagSubscription> tagSubscriptions;

    @OneToMany(cascade=CascadeType.ALL)
    public List<Attending> attending;

    @ManyToOne(optional = false)
    public List<User> friends;

    public static User authenticate(String email, String password) {
        return (User)User.find().where().eq("email",email).eq("password",password).findUnique();
    }

    public static Finder find() {
        return new Model.Finder(Integer.class, User.class);
    }

    public static List<User> getUsersByName(String name) {
        return find().where().eq("username",name).findList();
    }

    public String getFullName() {
        Logger.error("User.getFullName() is a stub!");
        return "Fakefirstname Fakelastname";
    }

    public List<String> getFeedTags(){
        return new ArrayList<String>(subscription);
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public void addTagSubscription(String tag) {
        List<TagSubscription> foundTags = TagSubscription.find().where().eq("user.id", this.id).eq("tagText", tag).findList();
        if (foundTags.size() == 1) {
            return;
        } else {
            TagSubscription tagSubscription = new TagSubscription();
            tagSubscription.user = this;
            tagSubscription.tagText = tag;
            tagSubscription.save();
        }
    }

    public boolean removeTagSubscription(String tag) {
        List<TagSubscription> foundTags = TagSubscription.find().where().eq("user.id", this.id).eq("tagText", tag).findList();
        if (foundTags.size() == 1) {
            foundTags.get(0).delete();
            return true;
        } else {
            return false;
        }
    }

    public List<String> getSubscribedTags() {
        List<String> resultingTags = new ArrayList<String>();
        List<TagSubscription> foundTags = TagSubscription.find().where().eq("user.id", this.id).findList();
        for (TagSubscription tagSubscription : foundTags) {
                resultingTags.add(tagSubscription.tagText);
        }
        return resultingTags;
    }

    public int getBirthyear() {
        return birthyear;
    }

    public String getPassword() {
        return password;
    }

}
