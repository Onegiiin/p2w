package by.kapinskiy.p2w.DTO;

public class OfferDTO {
    private int id;


    private int quantity;

    private long price;

    private String description;
    private LotDTO lot;
    private UserDTO executor;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserDTO getExecutor() {
        return executor;
    }

    public void setExecutor(UserDTO executor) {
        this.executor = executor;
    }

    public LotDTO getLot() {
        return lot;
    }

    public void setLot(LotDTO lot) {
        this.lot = lot;
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
}
