package by.kapinskiy.p2w.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "lot")
public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne()
    @JoinColumn(name = "category", referencedColumnName = "id")
    private Category category;

    @OneToMany(mappedBy = "lot", cascade = CascadeType.ALL)
    private List<Offer> offerList;

    @Column(name = "enable_multiple_offers")
    private boolean enableMultipleOffer;

    public List<Offer> getOfferList() {
        return offerList;
    }

    public void setOfferList(List<Offer> offerList) {
        this.offerList = offerList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isEnableMultipleOffer() {
        return enableMultipleOffer;
    }

    public void setEnableMultipleOffer(boolean enableMultipleOffer) {
        this.enableMultipleOffer = enableMultipleOffer;
    }
}
