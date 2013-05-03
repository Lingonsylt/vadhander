package controllers;

import models.Event;
import models.User;
import play.data.Form;
import play.mvc.*;

import views.html.createevent.index;

public class CreateEvent extends Controller {
  
    public static Result index() {
        Form<Event> eventForm = Form.form(Event.class);
        return ok(index.render(eventForm));
    }

    public static Result create()
    {
        // TODO: Remove when user registration is complete
        User user = new User();
        user.username = "username";
        user.firstname = "firstname";
        user.lastname = "lastname";
        user.password = "supersecret";
        user.birthyear = 1990;
        user.email = "user@name.com";
        user.save();

        Form<Event> eventForm = Form.form(Event.class);
        Event newEvent = eventForm.bindFromRequest().get();
        newEvent.creator = user;

        // TODO: Get this data from the form instead of hardcoding it
        // http://open.mapquestapi.com/nominatim/v1/search/se/Isafjordsgatan%2039,%20Kista?format=json
        newEvent.latitude = 59.4055219f;
        newEvent.longitude = 17.9448913f;
        newEvent.save();
        return ok(Integer.toString(newEvent.id));
    }
  
}
