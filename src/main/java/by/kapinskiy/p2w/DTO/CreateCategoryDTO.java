package by.kapinskiy.p2w.DTO;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;


public class CreateCategoryDTO {

    @NotBlank(message = "Name can't be empty")
    @Length(min = 3, max = 100, message = "Category name can't be less than 3 and more than 100 symbols")
    private String name;

    private Integer parentCategoryId;

    public Integer getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Integer parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
