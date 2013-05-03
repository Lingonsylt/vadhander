package controllers;

import models.Event;
import models.Tag;
import models.User;
import org.joda.time.DateTime;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.*;

import views.html.searchevents.index;

public class SearchEvents extends Controller {

    public static Result setupTestData() {
        User user = new User();
        user.username = "username";
        user.firstname = "firstname";
        user.lastname = "lastname";
        user.password = "supersecret";
        user.birthyear = 1990;
        user.email = "user@name.com";
        user.save();

        Event event = new Event();
        event.caption = "event1";
        event.creator = user;
        event.latitude = 59.4055219f;
        event.longitude = 17.9448913f;
        event.time_created = new DateTime();
        event.save();

        Event event2 = new Event();
        event2.caption = "event2";
        event2.creator = user;
        event2.latitude = 1;
        event2.longitude = 2;
        event2.time_created = new DateTime();
        event2.save();

        Tag tag = new Tag();
        tag.text = "foo";
        tag.event.add(event);
        tag.event.add(event2);
        tag.save();

        Tag tag2 = new Tag();
        tag2.text = "bar";
        tag2.event.add(event);
        tag2.save();

        return ok(index.render(null,"stuff added"));
    }

    public static List<String> stringToTagList(String input) {
        List<String> output = new ArrayList<>();
        String[] parsed = input.trim().split("#");
        for (String s : parsed) {
            s = s.trim();
            if (!s.equals("")) output.add(s);
        }
        return output;
    }
    public static List<Event> getEventsByUser(int id) {
        return Event.find().where().eq("creator_id",id).findList();
    }

    public static List<User> getUsersByName(String name) {
        return User.find().where().eq("name",name).findList();
    }

    public static List<Event> getEventsByTag(List<String> tags) {
        //TODO single sql query
        List<Tag> foundTags = Tag.find().where().in("text",tags).findList();
        Set<Event> events = new HashSet<>();
        for (Tag t : foundTags) {
            if (!events.contains(t.event)) events.addAll(t.event);
        }

        return new ArrayList(events);
    }
    public static Result index() {
        String input = Form.form().bindFromRequest().get("tag");
        if (input==null) return ok(index.render(null,""));
        List<Event> events = getEventsByTag(stringToTagList(input));
        return ok(index.render(events,input));
    }

}
