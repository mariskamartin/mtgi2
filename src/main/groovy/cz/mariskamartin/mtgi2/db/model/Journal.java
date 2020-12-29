package cz.mariskamartin.mtgi2.db.model;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class Journal {
    @Id
    private String uid;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    private String action;
    private String json;
    private boolean done;


    @PrePersist
    private void prePersist() {
        if (uid == null || uid.isEmpty() || uid.equals("0")) {
            this.uid = UUID.randomUUID().toString();
        }
        created = new Date();
    }

    public Journal() {
    }

    public Journal(String action, String json) {
        this.action = action;
        this.json = json;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getUid() {
        return uid;
    }

    public Date getCreated() {
        return created;
    }

    public String getAction() {
        return action;
    }

    public String getJson() {
        return json;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
