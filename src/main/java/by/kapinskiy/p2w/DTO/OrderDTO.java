package by.kapinskiy.p2w.DTO;

import by.kapinskiy.p2w.models.OrderStatus;

public class OrderDTO {
    private int id;

    private OrderStatus status;

    private UserDTO consumer;

    private OfferDTO offer;

    public UserDTO getConsumer() {
        return consumer;
    }

    public void setConsumer(UserDTO consumer) {
        this.consumer = consumer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OfferDTO getOffer() {
        return offer;
    }

    public void setOffer(OfferDTO offer) {
        this.offer = offer;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
