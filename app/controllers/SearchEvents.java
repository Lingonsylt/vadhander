package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.searchevents.index;

public class SearchEvents extends Controller {
  
    public static Result index() {
        return ok(index.render());
    }
  
}
