package controllers;

import models.Event;
import models.Tag;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.*;

import play.mvc.Security;
import views.html.searchevents.index;

public class SearchEvents extends Controller {

    public static List<Event> getEventsByUser(int id) {
        return Event.find().where().eq("creator_id",id).findList();
    }

    public static List<Event> getEventsByTag(List<String> tags) {
        Set <Event> events = Event.find().where().in("tags.text",tags).findSet();
        return new ArrayList<Event>(events);
    }
    @Security.Authenticated(Secured.class)
    public static Result index() {
        String input = Form.form().bindFromRequest().get("tag");
        if (input==null) return ok(index.render(null,""));
        List<Event> events = getEventsByTag(Tag.parseStringToList(input));
        return ok(index.render(events,input));
    }

}
