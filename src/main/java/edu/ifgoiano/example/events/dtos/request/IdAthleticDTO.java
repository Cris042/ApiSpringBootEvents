package edu.ifgoiano.example.events.dtos.request;

import java.util.UUID;

import javax.validation.constraints.NotBlank;


public class IdAthleticDTO 
{
    
    @NotBlank
    private UUID id;

    public UUID getId() 
    {
        return id;
    }

    public void setId(UUID id) 
    {
        this.id = id;
    }

   
}
