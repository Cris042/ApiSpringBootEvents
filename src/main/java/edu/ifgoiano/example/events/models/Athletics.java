package edu.ifgoiano.example.events.models;

import javax.persistence.*;

import java.util.UUID;

@Entity
public class Athletics 
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String course;

    public Athletics(String name, String description, String course) 
    {
        this.name = name;
        this.description = description;
        this.course = course;
    }

    public Athletics()
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
