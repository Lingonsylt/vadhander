package controllers;

import models.Event;
import models.Tag;
import models.User;
import org.joda.time.DateTime;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.*;

import views.html.searchevents.index;

public class SearchEvents extends Controller {

    public static List<String> stringToTagList(String input) {
        if (input.equals("") || input.equals("#") || !input.startsWith("#")) throw new IllegalArgumentException("invalid search phrase");
        List<String> output = Arrays.asList(input.split("#"));
        output = output.subList(1,output.size());
        return output;
    }
    public static List<Event> getEventsByUser(int id) {
        return Event.find().where().eq("creator_id",id).findList();
    }

    public static List<Event> getEventsByTag(List<String> tags) {
        List<Tag> foundTags = Tag.find().where().in("text",tags).findList();
        List<Event> events = new ArrayList<>();
        for (Tag t : foundTags) {
            if (!events.contains(t.event)) events.add(t.event);
        }
        return events;
    }
    public static Result doSearch() {
        String input = Form.form().bindFromRequest().get("tag");
        try {
            List<String> parsedTags = stringToTagList(input);
            List<Event> events = getEventsByTag(parsedTags);
            // TODO display events

            return ok(index.render(events));
        } catch (IllegalArgumentException e) {
            return ok(index.render(null));
        }
    }

    public static Result index() {
        return ok(index.render(null));
    }

}
