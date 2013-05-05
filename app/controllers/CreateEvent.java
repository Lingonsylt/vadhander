package controllers;

import models.Event;
import models.Tag;
import models.User;
import play.data.Form;
import play.mvc.*;

import views.html.createevent.index;

import java.util.List;

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
        /*
        Form<Event> eventForm = Form.form(Event.class);
        Event newEvent = eventForm.bindFromRequest().get();
        */
        Event newEvent = new Event();
        newEvent.caption = Form.form().bindFromRequest().get("eveName");
        newEvent.description = Form.form().bindFromRequest().get("eveDesc");
        List<String> stringTags = Tag.parseStringToList(Form.form().bindFromRequest().get("eveTags"));
        newEvent.tags = Tag.getTagList(stringTags);
        newEvent.creator = user;

        // TODO: Get this data from the form instead of hardcoding it
        // http://open.mapquestapi.com/nominatim/v1/search/se/Isafjordsgatan%2039,%20Kista?format=json
        newEvent.latitude = 59.4055219f;
        newEvent.longitude = 17.9448913f;
        newEvent.save();

        for (Tag t : newEvent.tags) { // bind event to tags to allow searching
            t.event.add(newEvent);
            t.save();
        }

        return ViewEvent.index(newEvent.id); // send user to the newly created event's view
    }
  
}
