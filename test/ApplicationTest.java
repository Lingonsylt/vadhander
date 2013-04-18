import java.util.*;

import models.*;
import org.codehaus.jackson.JsonNode;
import org.joda.time.DateTime;
import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest {

    @Test 
    public void simpleCheck() {
        int a = 1 + 1;
        assertThat(a).isEqualTo(2);
    }

    @Test
    public void testRelations() {
        running(fakeApplication(inMemoryDatabase()), new Runnable() {
            public void run() {

                User user = new User();
                user.name = "username";
                user.email = "user@name.com";

                user.save();

                // Check that the User was saved to DB
                assertThat(User.find().all()).hasSize(1);

                Event event = new Event();
                event.caption = "event #caption";
                event.creator = user;
                assertThat(event.tags).as("event.tags").isNotNull();
                event.time_created = new DateTime();

                event.save();
                // Check that the Event was saved to DB
                assertThat(Event.find().all()).hasSize(1);

                Tag tag = new Tag();
                tag.text = "tag text";
                tag.event = event;
                tag.save();

                Attending attending = new Attending();
                attending.user = user;
                attending.event = event;

                attending.save();

                // Check that the Attending was saved to DB
                assertThat(Attending.find().all()).hasSize(1);

                Comment comment = new Comment();
                comment.user = user;
                comment.text = "comment text";
                comment.event = event;
                comment.save();

                // Check that the comment can be fetched from DB
                List<Comment> loadedComments = Comment.find().all();
                assertThat(loadedComments).hasSize(1);
                Comment loadedComment = loadedComments.get(0);
                assertThat(loadedComment).isNotNull();

                // Check that all properties of the loadedUser are correct
                assertThat(loadedComment.user).as("loadedComment.user").isNotNull();
                assertThat(loadedComment.user.id).as("loadedComment.user.id").isEqualTo(user.id);
                assertThat(loadedComment.user).as("loadedComment.event").isNotNull();
                assertThat(loadedComment.user.id).as("loadedComment.event.id").isEqualTo(event.id);

                // Check that the event can be fetched from DB
                List<Event> loadedEvents = Event.find().all();
                assertThat(loadedEvents).hasSize(1);
                Event loadedEvent = loadedEvents.get(0);
                assertThat(loadedEvent).isNotNull();

                // Check that all properties of the loadedEvent are correct
                assertThat(loadedEvent.caption).isEqualTo("event #caption");
                assertThat(loadedEvent.creator).as("loadedEvent.creator").isNotNull();
                assertThat(loadedEvent.creator.id).as("loadedEvent.creator").isEqualTo(user.id);
                assertThat(loadedEvent.attending).as("loadedEvent.attending").hasSize(1);
                assertThat(loadedEvent.attending.get(0).user.id).as("loadedEvent.attending.get(0)").isEqualTo(user.id);
                assertThat(loadedEvent.comments).as("loadedEvent.comments").hasSize(1);

                // Check that the comment points to the correct user
                Comment eventComment = loadedEvent.comments.get(0);
                assertThat(eventComment).as("eventComment").isNotNull();
                assertThat(eventComment.user.id).as("eventComment.user.id").isEqualTo(user.id);

                // Verify the lazy loading of eventComment.user.comments
                assertThat(eventComment.user.comments).as("eventComment.user.comments").isNull();
                eventComment.user.refresh();
                assertThat(eventComment.user.comments).as("eventComment.user.comments").hasSize(1);

                // Check that userComment.id = eventComment.id
                Comment userComment = eventComment.user.comments.get(0);
                assertThat(userComment).as("userComment").isNotNull();
                assertThat(userComment.id).as("userComment.id = eventComment.id").isEqualTo(eventComment.id);
            }
        });
    }
}
