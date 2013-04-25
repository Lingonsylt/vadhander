import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.config.dbplatform.PostgresPlatform;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.ddl.DdlGenerator;
import controllers.SearchEvents;
import models.*;
import org.fluentlenium.core.search.Search;
import org.joda.time.DateTime;
import org.junit.*;

import play.Logger;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import static play.test.Helpers.fakeApplication;


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
    public void testTagSearch() {
        running(fakeApplication(inMemoryDatabase()), new Runnable() {
            public void run() {

                User user = new User();
                user.name = "username";
                user.email = "user@name.com";
                user.save();
                assertThat(User.find().all()).hasSize(1);

                Event event = new Event();
                event.caption = "event #caption";
                event.creator = user;
                event.latitude = 59.4055219f;
                event.longitude = 17.9448913f;
                event.time_created = new DateTime();
                event.save();
                assertThat(Event.find().all()).hasSize(1);

                Tag tag = new Tag();
                tag.text = "foo";
                tag.event = event;
                tag.save();
                assertThat(Tag.find().all()).hasSize(1);

                List<String> searchTags = new ArrayList<>();
                // search for non-existing tag
                searchTags.add("bar");
                Set<Event> foundEvents = SearchEvents.getEventsByTag(searchTags);
                assertThat(foundEvents).hasSize(0);

                searchTags.add("foo");
                // search for existing tag
                foundEvents = SearchEvents.getEventsByTag(searchTags);
                assertThat(foundEvents).hasSize(1);
            }
        });
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
                // http://open.mapquestapi.com/nominatim/v1/search/se/Isafjordsgatan%2039,%20Kista?format=json
                event.latitude = 59.4055219f;
                event.longitude = 17.9448913f;
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

    private String executeCommand(String command) {
        Process cmdProc = null;
        String output = "";
        try {
            String[] envp = { "PGPASSWORD=vadhander" };
            //Runtime.getRuntime().
            Logger.info("Executing command: " + command);
            cmdProc = Runtime.getRuntime().exec(command, envp);

            BufferedReader stdoutReader = new BufferedReader(
                    new InputStreamReader(cmdProc.getInputStream()));
            BufferedReader stderrReader = new BufferedReader(
                    new InputStreamReader(cmdProc.getErrorStream()));

            String line, error_line = null;
            while ((line = stdoutReader.readLine()) != null || (error_line = stderrReader.readLine()) != null) {
                if (line != null) {
                    Logger.info(line);
                    output += line;
                }
                if(error_line != null) {
                    output += error_line;
                    Logger.error(error_line);
                }

                if (line == null && error_line == null) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            cmdProc.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int retValue = cmdProc.exitValue();
        return output;
    }

    @Test
    public void testPostGIS() {
        Map<String, String> settings = new HashMap<String, String>();
        settings.put("db.default.url", "jdbc:postgresql://localhost/vadhander_test");
        settings.put("db.default.user", "vadhander");
        settings.put("db.default.password", "vadhander");
        running(fakeApplication(settings), new Runnable() {
            public void run() {
                EbeanServer server = Ebean.getServer("default");
                ServerConfig config = new ServerConfig();
                config.setDebugSql(true);
                DdlGenerator ddl = new DdlGenerator((SpiEbeanServer) server, new PostgresPlatform(), config);

                // drop
                String dropScript = ddl.generateDropDdl();
                ddl.runScript(false, dropScript);

                // create
                String createScript = ddl.generateCreateDdl();
                ddl.runScript(false, createScript);

                try {
                    ddl.runScript(false, "SELECT PostGIS_full_version();");
                } catch (java.lang.RuntimeException e) {
                    String shareDir = executeCommand("pg_config --sharedir");

                    Logger.info("Importing postgis_sql!");
                    Logger.info(executeCommand("psql -U vadhander -d vadhander_test -f " + shareDir + "/contrib/postgis-1.5/postgis.sql"));
                    Logger.info("Imported postgis_sql!");

                    Logger.info("Importing spatial_sys_ref!");
                    Logger.info(executeCommand("psql -U vadhander -d vadhander_test -f " + shareDir + "/contrib/postgis-1.5/spatial_ref_sys.sql"));
                    Logger.info("Imported spatial_sys_ref!");
                }
                try {
                    ddl.runScript(false, "SELECT AddGeometryColumn('event', 'coord', 3006, 'POINT', 2);");
                } catch (RuntimeException ex) {
                    Logger.warn("Could not create event.coord - already present?");
                }

                User user = new User();
                user.name = "username";
                user.email = "user@name.com";
                user.save();

                Event event = new Event();
                event.caption = "free #beer at antons place";
                event.creator = user;
                event.time_created = new DateTime();
                // http://open.mapquestapi.com/nominatim/v1/search/se/J%C3%A4ringegr%C3%A4nd%2017,%20Stockholm?format=json
                event.latitude = 59.3948344f;
                event.longitude = 17.8929762f;
                event.save();

                Event event2 = new Event();
                event2.caption = "free #beer at foo bar";
                event2.creator = user;
                event2.time_created = new DateTime();
                // http://open.mapquestapi.com/nominatim/v1/search/se/Isafjordsgatan%2039,%20Kista?format=json
                event2.latitude = 59.4055219f;
                event2.longitude = 17.9448913f;
                event2.save();

                List<Event> closeEvents = event.getCloseEvents(5);
                assertThat(closeEvents).hasSize(1);
                Event closeEvent = closeEvents.get(0);
                assertThat(closeEvent).isNotNull();
                assertThat(closeEvent.distance).isNotEqualTo(0f);
                assertThat(closeEvent.id).isEqualTo(event2.id);


            }
        });
    }
}
