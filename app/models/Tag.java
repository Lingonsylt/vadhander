package models;

import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Tag extends Model {
    public Tag(String text, Event event){
        this.text = text;
        this.event = event;
    }
    @Id
    public String text;

    @ManyToOne(optional = false)
    public Event event;
    public String toString(){
        return text;
    }
}
