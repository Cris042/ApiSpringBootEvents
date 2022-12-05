package edu.ifgoiano.example.events.models;

import javax.persistence.*;

import java.util.UUID;
@Entity
public class Attractions 
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
   
    public Attractions(String name, String description)
    {
        this.name = name;
        this.description = description;
    }

    public Attractions()
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
    
    public String getDescription() 
    {
        return description;
    }

    public void setDescription(String description) 
    {
        this.description = description;
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
