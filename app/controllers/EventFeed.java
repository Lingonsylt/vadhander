package controllers;

import models.Event;
import play.db.ebean.Model;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.eventfeed.index;

import java.util.List;

public class EventFeed extends Controller {
  
    public static Result index() {
        List<Event> events = new Model.Finder(Integer.class, Event.class).all();
        return ok(index.render());
    }
  
}
