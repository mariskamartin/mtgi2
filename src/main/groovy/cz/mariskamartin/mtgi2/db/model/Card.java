package cz.mariskamartin.mtgi2.db.model;

import com.google.common.base.MoreObjects;
import cz.mariskamartin.mtgi2.db.JpaEntityTraceListener;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.UUID;

@Entity
@EntityListeners(JpaEntityTraceListener.class)
//@XmlRootElement
//@Unique(members={"name","foil","rarity","edition"})
public class Card {
    /**
     * As one place for META names
     */
    public static enum PROPS {
        id, name, foil, created, updated, rarity, edition
    }

    @Id
    private String id;
    private String name;
    private boolean foil;
//    @Version
//    private long version;

//    @Column(nullable = false)
//    @Temporal(TemporalType.DATE)
    private Date created;
//    @Column(nullable = false)
//    @Temporal(TemporalType.DATE)
    private Date updated;

    @Enumerated(EnumType.STRING)
    private CardRarity rarity;
    @Enumerated(EnumType.STRING)
    private CardEdition edition;


    public static String getIdKey(Card card) {
        return card.name + "|" + String.valueOf(card.foil) + "|" + card.rarity + "|" + card.edition.getKey();
    }

    //because of JPA
    public Card() {}

    public Card(String name, boolean foil, CardRarity rarity, CardEdition edition) {
        this.name = name;
        this.foil = foil;
        this.rarity = rarity;
        this.edition = edition;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }

    public CardRarity getRarity() {
        return rarity;
    }

    public void setRarity(CardRarity rarity) {
        this.rarity = rarity;
    }

    public CardEdition getEdition() {
        return edition;
    }

    public void setEdition(CardEdition edition) {
        this.edition = edition;
    }

    public boolean isFoil() {
        return foil;
    }

    public void setFoil(boolean foil) {
        this.foil = foil;
    }

    @PrePersist
    private void prePersist() {
        if (id == null || id.isEmpty() || id.equals("0")) {
            this.id = getIdKey(this);
        }
        created = updated = new Date();
    }


    @PreUpdate
    private void preUpdate() {
        updated = new Date();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", getName())
                .add("r", getRarity())
                .add("e", getEdition())
                .add("f", isFoil())
                .toString();
    }
}