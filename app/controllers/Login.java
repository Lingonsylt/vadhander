

/**
 * Created with IntelliJ IDEA.
 * User: Michel
 * Date: 2013-05-03
 * Time: 12:08
 * To change this template use File | Settings | File Templates.
 */

package controllers;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.login.index;

public class Login extends Controller {

    public static Result index() {
        return ok(index.render());
    }

}
