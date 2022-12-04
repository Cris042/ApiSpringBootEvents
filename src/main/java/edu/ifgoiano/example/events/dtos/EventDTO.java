package edu.ifgoiano.example.events.dtos;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import org.springframework.hateoas.RepresentationModel;

import edu.ifgoiano.example.events.models.Athletics;
import edu.ifgoiano.example.events.models.Attractions;
import edu.ifgoiano.example.events.models.Categorys;
import edu.ifgoiano.example.events.models.Images;
import edu.ifgoiano.example.events.models.Places;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EventDTO extends RepresentationModel<EventDTO>
{
    
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String payment;
    @NotBlank
    private String date;

    private Set<Athletics> athletics = new HashSet<>();
    private Set<Attractions> attraction = new HashSet<>();
    private Set<Images> imagens = new HashSet<>();

    private Categorys category;
    private Places place;

    
    public EventDTO()
    {

    }

    public EventDTO
    (
        String name, String description, String payment, String date,Set<Athletics> athletics, 
        Set<Images> imagens, Set<Attractions> attraction, Categorys category, Places place
    ) 
    {   
        this.name = name;
        this.description = description;
        this.payment = payment;
        this.date = date;
        this.athletics = athletics;
        this.attraction = attraction;
        this.imagens = imagens;
        this.category = category;
        this.place = place;
    }
   
}
