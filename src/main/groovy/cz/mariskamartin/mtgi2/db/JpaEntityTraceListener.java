package cz.mariskamartin.mtgi2.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;


public class JpaEntityTraceListener {
    private static final Logger logger = LoggerFactory.getLogger(JpaEntityTraceListener.class);

    @PrePersist
    void onPrePersist(Object o) {
        logger.trace("prePersist: {}", o);
    }

    @PostPersist
    void onPostPersist(Object o) {
    }

    @PostLoad
    void onPostLoad(Object o) {
    }

    @PreUpdate
    void onPreUpdate(Object o) {
        logger.trace("preUpdate: {}", o);
    }

    @PostUpdate
    void onPostUpdate(Object o) {
    }

    @PreRemove
    void onPreRemove(Object o) {
        logger.trace("preRemove: {}", o);
    }

    @PostRemove
    void onPostRemove(Object o) {
    }
}
