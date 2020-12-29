package cz.mariskamartin.mtgi2.db.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

public class DailyCardInfoJpaListener {
    private static final Logger log = LoggerFactory.getLogger(DailyCardInfoJpaListener.class);

    @PrePersist
    public void methodInvokedBeforePersist(DailyCardInfo emp) {
        log.debug("persisting DailyCardInfo = {}", emp.toString());
    }

    @PostPersist
    public void methodInvokedAfterPersist(DailyCardInfo emp) {
        log.debug("Persisted DailyCardInfo = {}", emp.toString());
    }

    @PreUpdate
    public void methodInvokedBeforeUpdate(DailyCardInfo emp) {
        log.debug("Updating DailyCardInfo = {}", emp.toString());
    }

    @PostUpdate
    public void methodInvokedAfterUpdate(DailyCardInfo emp) {
        log.debug("Updated DailyCardInfo = {}", emp.toString());
    }

    @PreRemove
    private void methodInvokedBeforeRemove(DailyCardInfo emp) {
        log.debug("Removing DailyCardInfo = {}", emp.toString());
    }

    @PostRemove
    public void methodInvokedAfterRemove(DailyCardInfo emp) {
        log.debug("Removed DailyCardInfo = {}", emp.toString() );
    }
}
