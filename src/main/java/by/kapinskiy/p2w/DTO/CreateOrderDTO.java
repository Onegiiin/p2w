package by.kapinskiy.p2w.DTO;

import jakarta.validation.constraints.Min;

public class CreateOrderDTO {
    private int offerId;

    @Min(value = 1, message = "You can buy less than 1 item/service")
    private int quantity;

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
