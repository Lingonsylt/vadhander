package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.viewevent.index;

public class ViewEvent extends Controller {
  
    public static Result index(int id) {
        return ok(index.render());
    }
  
}
