

/**
 * Created with IntelliJ IDEA.
 * User: Michel
 * Date: 2013-05-03
 * Time: 12:08
 * To change this template use File | Settings | File Templates.
 */

package controllers;
import models.User;
import play.data.*;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.login.index;

public class Login extends Controller {
    public static Result logout(){
        session().clear();
        flash("success","You've been logged out");
        return redirect(routes.Login.index());
    }

    public static Result index() {
        return ok(index.render(Form.form(LogForm.class),""));
    }
    public static Result authenticate(){
        Form<LogForm> loginForm = Form.form(LogForm.class).bindFromRequest();
        String val = validate(loginForm.get().email,loginForm.get().password);
        if (val!=null) {
            return badRequest(index.render(loginForm,"Invalid email or password"));
        } else {
            session().clear();
            session("email",loginForm.get().email);
                return redirect(routes.EventFeed.index());
        }
    }
    public static String validate(String email, String password){
        if (User.authenticate(email, password) == null) {
            return "invalid user or password";
        }
        return null;
    }

    public static class LogForm {
        public String email;
        public String password;
    }

}
