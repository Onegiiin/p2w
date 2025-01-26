package by.kapinskiy.p2w.DTO;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class LotDTO {

    private int id;

    @NotBlank(message = "Name can't be empty")
    @Length(min = 3, max = 100, message = "Lot name can't be less than 3 and more than 100 symbols")
    private String name;

    private CategoryDTO category;

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
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



}
