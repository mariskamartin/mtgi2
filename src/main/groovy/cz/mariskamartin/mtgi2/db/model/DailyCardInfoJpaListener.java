package cz.mariskamartin.mtgi2.db.model;

import javax.persistence.*;

public class DailyCardInfoJpaListener {

    @PrePersist
    public void methodInvokedBeforePersist(DailyCardInfo emp) {
        System.out.println("persisting DailyCardInfo = " + emp);
    }

    @PostPersist
    public void methodInvokedAfterPersist(DailyCardInfo emp) {
        System.out.println("Persisted DailyCardInfo = " + emp);
    }

    @PreUpdate
    public void methodInvokedBeforeUpdate(DailyCardInfo emp) {
        System.out.println("Updating DailyCardInfo = " + emp);
    }

    @PostUpdate
    public void methodInvokedAfterUpdate(DailyCardInfo emp) {
        System.out.println("Updated DailyCardInfo = " + emp);
    }

    @PreRemove
    private void methodInvokedBeforeRemove(DailyCardInfo emp) {
        System.out.println("Removing DailyCardInfo = " + emp);
    }

    @PostRemove
    public void methodInvokedAfterRemove(DailyCardInfo emp) {
        System.out.println("Removed DailyCardInfo = " + emp );
    }
}
