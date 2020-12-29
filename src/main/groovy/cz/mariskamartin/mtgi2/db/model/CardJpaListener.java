package cz.mariskamartin.mtgi2.db.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

public class CardJpaListener {
    private static final Logger log = LoggerFactory.getLogger(CardJpaListener.class);

    @PrePersist
    public void methodInvokedBeforePersist(Card emp) {
        log.debug("persisting card = {}", emp.toString());
    }

    @PostPersist
    public void methodInvokedAfterPersist(Card emp) {
        log.debug("Persisted card = {}", emp.toString());
    }

    @PreUpdate
    public void methodInvokedBeforeUpdate(Card emp) {
        log.debug("Updating card = ", emp.toString());
    }

    @PostUpdate
    public void methodInvokedAfterUpdate(Card emp) {
        log.debug("Updated card = {}", emp.toString());
    }

    @PreRemove
    private void methodInvokedBeforeRemove(Card emp) {
        log.debug("Removing card = {}", emp.toString());
    }

    @PostRemove
    public void methodInvokedAfterRemove(Card emp) {
        log.debug("Removed card = {}", emp.toString() );
    }
}
