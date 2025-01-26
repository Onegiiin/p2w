package by.kapinskiy.p2w.DTO;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;


public class CreateLotDTO {

    @NotBlank(message = "Name can't be empty")
    @Length(min = 3, max = 100, message = "Lot name can't be less than 3 and more than 100 symbols")
    private String name;

    private int categoryId;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
