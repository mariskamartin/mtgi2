package cz.mariskamartin.mtgi2.db.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.slf4j.Logger;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

@JsonIgnoreProperties(ignoreUnknown = true, value = "log")
public abstract class Auditable {
    abstract Logger getLog();

    @PostPersist
    void postPersist() {
        getLog().debug("postPersist: {}", this);
    }

    @PostUpdate
    void postUpdate() {
        getLog().debug("postUpdate: {}", this);
    }

    @PostRemove
    void postRemove() {
        getLog().debug("postRemove: {}", this);
    }
}
