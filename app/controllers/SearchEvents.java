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

    public static List<Event> getEventsByUser(int id) {
        return Event.find().where().eq("creator_id",id).findList();
    }

    public static List<Event> getEventsByTag(List<String> tags) {
        //TODO single sql query
        Set<Tag> foundTags = Tag.find().where().in("text",tags).findSet();
        Set<Event> events = new HashSet<Event>();
        for (Tag t : foundTags) {
            if (!events.contains(t.event)) events.add(t.event);
        }

        return new ArrayList<Event>(events);
    }
    public static Result index() {
        String input = Form.form().bindFromRequest().get("tag");
        if (input==null) return ok(index.render(null,""));
        List<Event> events = getEventsByTag(Tag.parseStringToList(input));
        return ok(index.render(events,input));
    }

}
