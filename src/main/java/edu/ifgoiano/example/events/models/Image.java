package edu.ifgoiano.example.events.models;

import javax.persistence.*;

import java.util.UUID;

@Entity
@Table
( 
    name = "image",
    uniqueConstraints = 
    {
       @UniqueConstraint(columnNames = "name"),
    }
)
public class Image 
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String name;

    private String url;

    public Image(String name, String url)
    {
        this.name = name;
        this.url = url;
    }

    public Image()
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
