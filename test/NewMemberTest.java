import java.lang.Integer;
import java.util.*;
import models.*;
import org.joda.time.DateTime;
import org.junit.Test;
import play.mvc.Result;
import play.mvc.*;
import org.junit.*;

import java.lang.String;
import play.db.ebean.Model;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;
import static play.test.Helpers.fakeApplication;
import static org.fest.assertions.Assertions.*;
import play.test.*;

public class NewMemberTest {
    /**
     * Test that a new member is created from form
     * https://github.com/Lingonsylt/playblogjava/blob/feature/tags/test/ApplicationTest.java
     */
    @Test
    public void testIndex() {
        running(fakeApplication(inMemoryDatabase()), new Runnable() {
            public void run() {
                User user = new User();
                user.save();

                String testUsername = "Username1";
                String testFirstName = "Firstname1";
                String testLastName = "Lastname1";
                String testBirthYear = "1999";
                String testEmail = "Email1";
                String testPassword = "Password1";

                Map<String, String> requestParams = new HashMap<String, String>();
                requestParams.put("username", testUsername);
                requestParams.put("firstname", testFirstName);
                requestParams.put("lastname", testLastName);
                requestParams.put("birthyear", testBirthYear);
                requestParams.put("email", testEmail);
                requestParams.put("password", testPassword);

                FakeRequest request = fakeRequest(POST, "controllers.routes.NewMember.createUser()").withFormUrlEncodedBody(requestParams);


                /*Result result = callAction(
                        controllers.routes.NewMember.createUser(),
                        request
                );*/

                //assertThat(status(result)).isEqualTo(SEE_OTHER);

                List finderResult = new Model.Finder(int.class, User.class).all();
                assertThat(finderResult.size()).isEqualTo(1);

                User user2 = new User();
                user2 = (User)finderResult.get(0);
                user2.save();

                /*assertThat(user2).isNotNull();
                assertThat(user2.getUsername()).isEqualTo(testUsername);
                assertThat(user2.firstname).isEqualTo(testFirstName);
                assertThat(user2.lastname).isEqualTo(testLastName);
                assertThat(user2.birthyear).isEqualTo(Integer.parseInt(testBirthYear));
                assertThat(user2.email).isEqualTo(testEmail);
                assertThat(user2.password).isEqualTo(testPassword);*/

                user2.refresh();

            }
        });
    }
}
