package controllers;

import models.Event;
import models.Tag;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchEvents extends Controller {
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
