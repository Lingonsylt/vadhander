package controllers;

import models.Event;
import models.Tag;
import models.User;
import play.data.Form;
import play.mvc.*;
import views.html.createevent.index;

import java.util.ArrayList;
import java.util.List;

public class CreateEvent extends Controller {
  
    public static Result index() {
        Form<Event> eventForm = Form.form(Event.class);
        return ok(index.render(eventForm));
    }
    public static List<Tag> tags;

    public static Result create()
    {
        // TODO: Remove when user registration is complete
        User user = new User();
        user.name = "username";
        user.email = "user@name.com";
        user.save();

        Form<Event> eventForm = Form.form(Event.class);
        eventForm = eventForm.bindFromRequest();
        if(eventForm.hasErrors())
            return ok(index.render(eventForm));
        else{
            //if(event.eventTime <  currenttime..  || event.eventTime ... ){}
            //if(event.Tags.getSize() == 0){}
            //if(user.name == null || user.email == null  username???)

            Event newEvent = eventForm.get();
            newEvent.addTag("Kista");
            newEvent.creator = user;
            newEvent.timeCreated = newEvent.timeCreated.now();
            //newEvent.tags = newEvent.taglist;
            // TODO: Get this data from the form instead of hardcoding it
            // http://open.mapquestapi.com/nominatim/v1/search/se/Isafjordsgatan%2039,%20Kista?format=json
            newEvent.latitude = 59.4055219f;
            newEvent.longitude = 17.9448913f;
            newEvent.save();
            return redirect(routes.ViewEvent.index(newEvent.id));
        }


    }


}

