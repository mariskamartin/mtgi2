package cz.mariskamartin.mtgi2.db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
import cz.mariskamartin.mtgi2.db.JpaEntityTraceListener;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Entity
@EntityListeners(JpaEntityTraceListener.class)
public class DailyCardInfo {
    /**
     * As one place for META names
     */
    public static enum PROPS {
        id, price, storeAmount, shop, day, card
    }

    @Id
    private String id;

    private BigDecimal price;

    private long storeAmount;

    @Enumerated(EnumType.STRING)
    private CardShop shop;

    @Temporal(TemporalType.DATE)
//    @Temporal(TemporalType.TIMESTAMP)
    private Date day;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="card_id", nullable = false)
    private Card card;

    public static String getIdKey(DailyCardInfo dailyCardInfo) {
        String day = new SimpleDateFormat("yyyy-MM-dd").format(dailyCardInfo.day);
        return "DCI|" + dailyCardInfo.shop + "|" + day + "|" + dailyCardInfo.getCard().getId();
    }

    public DailyCardInfo() {
        // TODO Auto-generated constructor stub
    }

    public DailyCardInfo(Card card, BigDecimal price, long storeAmount, Date date, CardShop shop, String id) {
        super();
        this.shop = shop;
        this.card = card;
        this.price = price;
        this.storeAmount = storeAmount;
        this.setDay(date);
        this.id = id;
    }

    @XmlTransient
    @JsonIgnore
    public Card getCard() {
        return card;
    }

    @XmlTransient
    @JsonIgnore
    public String getId() {
        return id;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public long getStoreAmount() {
        return storeAmount;
    }

    public void setStoreAmount(long storeAmount) {
        this.storeAmount = storeAmount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getDay() {
        return day;
    }

    public String getDayTxt() {
        return new SimpleDateFormat("yyyy-MM-dd").format(day);
    }

    public void setDay(Date day) {
        this.day = new Date(day.getTime());
    }

    public CardShop getShop() {
        return shop;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
//                .add("card", card != null ? card.toString() : "null")
//                .add("cardId", card != null ? card.getId() : "null")
                .add("shop", getShop())
                .add("cena", price)
                .add("skladem", storeAmount)
                .add("day", day)
                .toString();
    }

    @PrePersist
    private void prePersist() {
        if (id == null || id.isEmpty() || id.equals("0")) {
//            this.id = "DCI-" + UUID.randomUUID().toString();
            this.id = getIdKey(this);
        }
    }
}
