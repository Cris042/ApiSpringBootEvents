package edu.ifgoiano.example.events.models;

import javax.persistence.*;

import java.util.UUID;

@Entity
public class Images 
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String name;

    private String url;

    public Images(String name, String url)
    {
        this.name = name;
        this.url = url;
    }

    public Images()
    {
        
    }
    
    public String getUrl() 
    {
        return url;
    }

    public void setUrl(String url) 
    {
        this.url = url;
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
