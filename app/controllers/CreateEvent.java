package controllers;

import models.Event;
import models.Tag;
import models.User;
import play.data.Form;
import play.mvc.*;

import views.html.createevent.index;

import java.util.List;

public class CreateEvent extends Controller {
    @Security.Authenticated(Secured.class)
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
        eventForm = eventForm.bindFromRequest();

        if(eventForm.hasErrors())
            return ok(index.render(eventForm));
        else{
            Event newEvent = eventForm.get();
            newEvent.creator = user;
            newEvent.time_created = newEvent.time_created.now();
            // TODO: Get this data from the form instead of hardcoding it
            // http://open.mapquestapi.com/nominatim/v1/search/se/Isafjordsgatan%2039,%20Kista?format=json
            newEvent.latitude = 59.4055219f;
            newEvent.longitude = 17.9448913f;
            List<String> stringTags = Tag.parseStringToList(Form.form().bindFromRequest().get("eveTags"));
            for (String str : stringTags) {
                newEvent.tags.add(new Tag(str,newEvent));
            }
            newEvent.save();
            return redirect(routes.ViewEvent.index(newEvent.id)); // send user to the newly created event's view
        }
    }
  
}
