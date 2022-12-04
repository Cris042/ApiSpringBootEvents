package edu.ifgoiano.example.events.dtos;

import javax.validation.constraints.NotBlank;

import org.springframework.hateoas.RepresentationModel;

public class AthleticDTO extends RepresentationModel<AthleticDTO>
{
    
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String course;

    public AthleticDTO(String name, String description, String course) 
    {
        this.name = name;
        this.description = description;
        this.course = course;
    }

    public AthleticDTO()
    {
        
    }
    
    public String getDescription() 
    {
        return description;
    }

    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getCourse()
    {
        return course;
    }

    public void setCourse(String course) 
    {
        this.course = course;
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
