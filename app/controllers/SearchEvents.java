package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;
import models.Event;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.searchevents.index;

import java.util.List;

public class SearchEvents extends Controller {

    public static List<Event> getEventsByTag(List<String> tags) {
        String sql = "SELECT id, caption, latitude, longitude, time_created, creator_id " +
                "FROM tag, event" +
                "WHERE event_id=id AND ";
        for (String s : tags) {
            sql+= "text='"+s+"'";
            if (tags.indexOf(s)!=tags.size()-1) sql+=" OR ";
        }
        RawSql rawSql = RawSqlBuilder.unparsed(sql)
                .columnMapping("id",  "id")
                .columnMapping("caption",  "caption")
                .columnMapping("latitude",  "latitude")
                .columnMapping("longitude",  "longitude")
                .columnMapping("time_created",  "time_created")
                .columnMapping("creator_id",  "creator_id")
                .create();

        com.avaje.ebean.Query<Event> query = Ebean.find(Event.class);
        query.setRawSql(rawSql);

        return query.findList();
    }

    public static Result index() {
        // TODO retrieve list of tags from search field
        return ok(index.render());
    }
  
}
