package controllers;

import play.*;
import play.mvc.*;
import views.html.newmember.index;
import play.data.Form;
import models.User;

public class NewMember extends Controller {
  
    public static Result index() {
        Form userForm = Form.form(User.class);

        return ok(index.render(userForm));
    }

    public static Result createUser() {
        Form userForm = Form.form(User.class).bindFromRequest();

        if(userForm.hasErrors()) {
            return ok(index.render(userForm));
        } else {
            User user = (User)userForm.get();
            user.save();
            return redirect(routes.Application.index());
        }

    }

}
