package controllers;

import models.Event;
import models.Tag;
import models.User;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import views.html.searchevents.index;

public class SearchEvents extends Controller {

    public static List<String> stringToTagList(String input) {
        // throw exception if the search phrase doesn't follow the correct syntax
        if (input.equals("") || input.equals("#") || !input.startsWith("#")) throw new IllegalArgumentException("invalid search phrase");
        List<String> output = Arrays.asList(input.split("#"));
        output = output.subList(1,output.size());
        return output;
    }
    public static List<Event> getEventsByUser(int id) {
        return Event.find().where().eq("creator_id",id).findList();
    }

    public static Set<Event> getEventsByTag(List<String> tags) {
        return Tag.find().fetch("event").where().in("text",tags).findSet();
    }
    public static Result doSearch() {
        // retrieve search phrase
        String input = Form.form().bindFromRequest().get("tag");
        try {
            // find events from existing tags extracted from parsed search phrase
            Set<Event> events = getEventsByTag(stringToTagList(input));
            // TODO display events

            // temporary solution; redirects to a page showing amount of events found
            return ok("search found "+events.size()+" events");
        } catch (IllegalArgumentException e) {
            // return to search screen if invalid search input was entered
            // TODO include error message
            return ok(index.render());
        }
    }

    public static Result index() {
        return ok(index.render());
    }

}
