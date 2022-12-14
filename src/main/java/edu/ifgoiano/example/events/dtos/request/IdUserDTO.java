package edu.ifgoiano.example.events.dtos.request;

import java.util.UUID;

import javax.validation.constraints.NotBlank;

public class IdUserDTO 
{    
    @NotBlank
    private UUID id;

    public IdUserDTO(UUID id)
    {
        this.id = id;
    }

    public IdUserDTO()
    {
    }

    public UUID getId() 
    {
        return id;
    }

    public void setId(UUID id) 
    {
        this.id = id;
    }
}
