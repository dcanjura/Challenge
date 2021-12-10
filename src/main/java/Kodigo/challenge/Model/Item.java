package Kodigo.challenge.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Items")
@Getter @Setter
public class Item {

    @Id
    private long itemId;

    private String itemName;
    private String itemEnteredByUser;
    private LocalDate itemEnteredDate;
    private Double itemBuyingPrice;
    private Double itemSellingPrice;
    private LocalDate itemLastModifiedDate = LocalDate.now();
    private String itemLastModifiedByUser;
    private String itemStatus;

    public Item(){ super(); }

    public Item(long itemId, String itemName, String itemEnteredByUser, LocalDate itemEnteredDate, Double itemBuyingPrice, Double itemSellingPrice, LocalDate itemLastModifiedDate, String itemLastModifiedByUser, String itemStatus) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemEnteredByUser = itemEnteredByUser;
        this.itemEnteredDate = itemEnteredDate;
        this.itemBuyingPrice = Math.round(itemBuyingPrice * 10.0) / 10.0;
        this.itemSellingPrice = Math.round(itemSellingPrice * 10.0) / 10.0;
        this.itemLastModifiedDate = itemLastModifiedDate;
        this.itemLastModifiedByUser = itemLastModifiedByUser;
        this.itemStatus = itemStatus;
    }
}
