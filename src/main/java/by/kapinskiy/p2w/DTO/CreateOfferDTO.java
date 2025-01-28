package by.kapinskiy.p2w.DTO;

import jakarta.validation.constraints.Min;

public class CreateOfferDTO {

    @Min(value = 0, message = "Quantity can't be less than 0")
    private int quantity;

    @Min(value = 0, message = "Price can't be less than 0")
    private long price;

    private String description;

    private int lotId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getLotId() {
        return lotId;
    }

    public void setLotId(int lotId) {
        this.lotId = lotId;
    }
}
