package controllers;

import models.Event;
import models.Tag;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import views.html.viewevent.index;

public class SearchEvents extends Controller {

    public static List<String> stringToTagList(String input) {
        if (input.equals("") || input.equals("#") || !input.startsWith("#")) throw new IllegalArgumentException("invalid tag");
        List<String> output = Arrays.asList(input.split("#"));
        output = output.subList(1,output.size());
        return output;
    }

    public static Set<Event> getEventsByTag(List<String> tags) {
        List<Tag> foundTags = Tag.find().where().in("text",tags).findList();
        Set<Event> foundEvents = new HashSet<Event>();
        for (Tag t : foundTags) {
            foundEvents.add(t.event);
        }
        return foundEvents;
    }
  
    public static Result index() {
        return ok(index.render());
    }
  
}
