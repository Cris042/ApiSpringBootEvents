package edu.ifgoiano.example.events.models;

import javax.persistence.*;

import java.util.UUID;
@Entity
public class Categorys 
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String name;

    public Categorys( UUID id,String name) 
    {
        this.id = id;
        this.name = name;
    }

    public Categorys()
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

    public String getName() 
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }
}
