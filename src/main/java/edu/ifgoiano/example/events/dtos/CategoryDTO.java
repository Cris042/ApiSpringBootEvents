package edu.ifgoiano.example.events.dtos;

import javax.validation.constraints.NotBlank;

import org.springframework.hateoas.RepresentationModel;

public class CategoryDTO extends RepresentationModel<CategoryDTO>
{
    @NotBlank
    private String name;

    public CategoryDTO(String name) 
    {
        this.name = name;
    }

    public CategoryDTO()
    {
        
    }

    public String getName() 
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }
}
