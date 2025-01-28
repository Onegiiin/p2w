package by.kapinskiy.p2w.DTO;

import by.kapinskiy.p2w.models.OrderStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateOrderDTO {
    @NotNull(message = "Status cannot be null")
    private OrderStatus status;

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
