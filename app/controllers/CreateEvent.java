package controllers;

import play.*;
import play.mvc.*;

import views.html.*;
import views.html.createevent.index;

public class CreateEvent extends Controller {
  
    public static Result index() {
        return ok(index.render());
    }
  
}
