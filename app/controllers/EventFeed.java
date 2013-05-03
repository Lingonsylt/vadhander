package controllers;

import models.Event;
import models.User;
import play.db.ebean.Model;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.eventfeed.index;

import java.util.List;

public class EventFeed extends Controller {
  
    public static Result index() {
        User u = new User();
        u.subscription.add("foo"); u.subscription.add("bar");
        List<Event> events = SearchEvents.getEventsByTag(u.getFeedTags());
        return ok(index.render(events));
    }
  
}
