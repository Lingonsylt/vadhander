package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tag extends Model {
    @Id
    public String text;

    @ManyToMany
    public List<Event> event;
    public Tag(){
        event = new ArrayList<>();
    }

    public static Finder find() {
        return new Model.Finder(Integer.class, Tag.class);
    }

    public static List<Tag> getTagList(List<String> stringTags) {
        List<Tag> tags = new ArrayList<Tag>();
        for (String str : stringTags) {
            Tag t = (Tag)Tag.find().where().eq("text",str).findUnique();
            if (t==null) { // tag does not exist -> create tag
                t = new Tag();
                t.text = str;
                t.save();
            }
            tags.add(t);
        }
        return tags;
    }

    public static List<String> parseStringToList(String input) {
        List<String> output = new ArrayList<>();
        String[] parsed = input.trim().split("#");
        for (String s : parsed) {
            s = s.trim();
            if (!s.equals("")) output.add(s);
        }
        return output;
    }
}
