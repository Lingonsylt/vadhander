import models.*;
import org.joda.time.DateTime;
import org.junit.Test;
import play.mvc.Result;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

public class ViewEventTest {
    @Test
    public void testIndex() {
        running(fakeApplication(inMemoryDatabase()), new Runnable() {
            public void run() {

                User user = new User();
                user.name = "username";
                user.email = "user@name.com";
                user.save();

                Event event = new Event();
                event.caption = "event #caption";
                event.creator = user;
                // http://open.mapquestapi.com/nominatim/v1/search/se/Isafjordsgatan%2039,%20Kista?format=json
                event.latitude = 59.4055219f;
                event.longitude = 17.9448913f;
                event.time_created = new DateTime();

                event.save();
                assertThat(Event.find().all()).hasSize(1);

                Tag tag = new Tag();
                tag.text = "tag text";
                tag.event.add(event);
                tag.save();

                Attending attending = new Attending();
                attending.user = user;
                attending.event = event;
                attending.save();

                Comment comment = new Comment();
                comment.user = user;
                comment.text = "comment text";
                comment.event = event;
                comment.save();

                Result result = callAction(
                        controllers.routes.ref.ViewEvent.index(event.id)
                );

                assertThat(status(result)).isEqualTo(OK);
                assertThat(contentAsString(result)).contains(event.caption);
                assertThat(contentAsString(result)).contains(comment.text);
            }
        });
    }
}
