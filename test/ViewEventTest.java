import models.*;
import org.joda.time.DateTime;
import org.junit.Test;
import play.mvc.Result;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

public class ViewEventTest {
    /**
     * Test that the rendering of an event results in the rendered page containing the event caption and comment text
     */
    @Test
    public void testIndex() {
        running(fakeApplication(inMemoryDatabase()), new Runnable() {
            public void run() {

                // Create a new user
                User user = new User();
                user.username = "username";
                user.firstname = "firstname";
                user.lastname = "lastname";
                user.birthyear = 1999;
                user.email = "user@name.com";
                user.password = "password";

                user.save();

                // Create a new event with user as creator
                Event event = new Event();
                event.caption = "event #caption";
                event.creator = user;
                // http://open.mapquestapi.com/nominatim/v1/search/se/Isafjordsgatan%2039,%20Kista?format=json
                event.latitude = 59.4055219f;
                event.longitude = 17.9448913f;
                event.time_created = new DateTime();

                event.save();
                assertThat(Event.find().all()).hasSize(1);

                // Add a tag to the event
                Tag tag = new Tag();
                tag.text = "tag text";
                tag.event.add(event);
                tag.save();

                // Make the user attend the event
                Attending attending = new Attending();
                attending.user = user;
                attending.event = event;
                attending.save();

                // Add a comment to the event
                Comment comment = new Comment();
                comment.user = user;
                comment.text = "comment text";
                comment.event = event;
                comment.save();

                // Call ViewEvent.index with event.id
                Result result = callAction(
                        controllers.routes.ref.ViewEvent.index(event.id)
                );

                // Check that the rendered page contains the event caption and the comment text and that the
                // response is a 200 OK
                assertThat(status(result)).isEqualTo(OK);
                assertThat(contentAsString(result)).contains(event.caption);
                assertThat(contentAsString(result)).contains(comment.text);
            }
        });
    }
}
