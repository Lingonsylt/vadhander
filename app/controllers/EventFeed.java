package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.eventfeed.index;

public class EventFeed extends Controller {
  
    public static Result index() {
        return ok(index.render());
    }
  
}
