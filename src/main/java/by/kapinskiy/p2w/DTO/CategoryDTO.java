package by.kapinskiy.p2w.DTO;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;


public class CategoryDTO {
    private int id;

    @NotBlank(message = "Name can't be empty")
    @Length(min = 3, max = 100, message = "Category name can't be less than 3 and more than 100 symbols")
    private String name;

    private CategoryDTO parentCategory;

    public CategoryDTO getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(CategoryDTO parentCategory) {
        this.parentCategory = parentCategory;
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
