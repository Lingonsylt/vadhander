package controllers;

import models.Attending;
import models.Comment;
import models.Event;
import models.User;
import org.joda.time.DateTime;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.viewevent.index;

import java.util.ArrayList;

public class ViewEvent extends Controller {
    /**
     * Create a fake event for use by ViewEvent.index, until CreateEvent is finished.
     */
    private static Event getFakeEvent(int id) {
        // Create a user
        User user = new User();
        user.name = "username";
        user.email = "user@name.com";

        // Create an event, with user as creator
        Event fakeEvent = new Event();
        fakeEvent.id = id;
        fakeEvent.caption = "event #caption";
        fakeEvent.creator = user;
        fakeEvent.latitude = 59.4055219f;
        fakeEvent.longitude = 17.9448913f;
        fakeEvent.time_created = new DateTime();

        fakeEvent.description = "This is a quite short fake event description. The event is gonna be awesome! " +
                "Yes it is. Yes it is...";
        fakeEvent.road_description = "Take the subway to the last stop. Then go back again.";
        fakeEvent.comments = new ArrayList<Comment>();

        /*
        // Create a list with attendants
        fakeEvent.attending = new ArrayList<Attending>();
        Attending attending1 = new Attending();
        User fakeUser = new User();
        attending1.user = fakeUser;
        fakeEvent.attending.add(attending1);
        */

        //The time that shows for when the event is about to take place.
        fakeEvent.event_time = "2013-06-06 15:00";

        //Location for the event.
        fakeEvent.location = "Fake location.";

        // Create a comment and add it to the fake event
        Comment comment = new Comment();
        comment.user = user;
        comment.text = "This is a fake comment";
        fakeEvent.comments.add(comment);

        // Create another comment and add it to the fake event
        Comment comment2 = new Comment();
        comment2.user = user;
        comment2.text = "This is a fake comment. This is a fake comment. This is a fake comment. " +
                "This is a fake comment. This is a fake comment. This is a fake comment. This is a fake comment. " +
                "This is a fake comment. This is a fake comment. This is a fake comment. This is a fake comment. " +
                "This is a fake comment. ";
        fakeEvent.comments.add(comment2);
        return fakeEvent;
    }

    public static Result index(int id) {
        // Try to fetch the event from DB, will be null if not found
        // Let the template handle the null-case
        Event event = (Event)Event.find().byId(id);

        // Return a fake event if id == -1, since there is no way to create events yet
        // TODO: Remove when create event is done
        if (id == -1) {
            event = getFakeEvent(-1);
        }

        // Render index with the event, letting it handle it if event is null
        return ok(index.render(event));
    }
  
}
