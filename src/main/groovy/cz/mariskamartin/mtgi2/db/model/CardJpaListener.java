package cz.mariskamartin.mtgi2.db.model;

import javax.persistence.*;

public class CardJpaListener {

    @PrePersist
    public void methodInvokedBeforePersist(Card emp) {
        System.out.println("persisting card = " + emp);
    }

    @PostPersist
    public void methodInvokedAfterPersist(Card emp) {
        System.out.println("Persisted card = " + emp);
    }

    @PreUpdate
    public void methodInvokedBeforeUpdate(Card emp) {
        System.out.println("Updating card = " + emp);
    }

    @PostUpdate
    public void methodInvokedAfterUpdate(Card emp) {
        System.out.println("Updated card = " + emp);
    }

    @PreRemove
    private void methodInvokedBeforeRemove(Card emp) {
        System.out.println("Removing card = " + emp);
    }

    @PostRemove
    public void methodInvokedAfterRemove(Card emp) {
        System.out.println("Removed card = " + emp );
    }
}
