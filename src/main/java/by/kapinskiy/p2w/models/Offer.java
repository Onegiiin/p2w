package by.kapinskiy.p2w.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "offer")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne()
    @JoinColumn(name = "lot", referencedColumnName = "id")
    private Lot lot;

    @ManyToOne()
    @JoinColumn(name = "executor", referencedColumnName = "id")
    private User executor;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    @NotBlank(message = "Offer must have a price")
    @Min(value = 0, message = "Price can't be less than 0")
    private long price;

    @Column(name = "description")
    private String description;

    public User getExecutor() {
        return executor;
    }

    public void setExecutor(User executor) {
        this.executor = executor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public Lot getProduct() {
        return lot;
    }

    public void setProduct(Lot lot) {
        this.lot = lot;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
