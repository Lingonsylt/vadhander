package controllers;

import models.Comment;
import models.Event;
import models.User;
import org.joda.time.DateTime;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.viewevent.index;

import java.util.ArrayList;

public class ViewEvent extends Controller {
    private static Event getFakeEvent(int id) {
        User user = new User();
        user.name = "username";
        user.email = "user@name.com";

        Event fakeEvent = new Event();
        fakeEvent.id = id;
        fakeEvent.caption = "event #caption";
        fakeEvent.creator = user;
        fakeEvent.latitude = 59.4055219f;
        fakeEvent.longitude = 17.9448913f;
        fakeEvent.time_created = new DateTime();
        fakeEvent.comments = new ArrayList<Comment>();
        Comment comment = new Comment();
        comment.user = user;
        comment.text = "This is a fake comment";
        fakeEvent.comments.add(comment);
        return fakeEvent;
    }

    public static Result index(int id) {
        //fakeSetup(id);
        Event event = (Event)Event.find().byId(id);

        // TODO: Remove when create event is done
        if (id == -1) {
            event = getFakeEvent(-1);
        }

        return ok(index.render(event));
    }
  
}
