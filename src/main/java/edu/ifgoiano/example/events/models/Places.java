package edu.ifgoiano.example.events.models;

import javax.persistence.*;

import java.util.UUID;

@Entity
public class Places 
{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String capacity;
    @Column(nullable = false)
    private String latitude;
    @Column(nullable = false)
    private String longitude;

    public Places(UUID id, String name, String latitude, String longitude, String capacity) 
    {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.capacity = capacity;
    }

    public Places()
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

    public String getCapacity() 
    {
        return capacity;
    }

    public void setCapacity(String capacity) 
    {
        this.capacity = capacity;
    }

    public String getLatitude() 
    {
        return latitude;
    }

    public void setLatitude(String latitude) 
    {
        this.latitude = latitude;
    }

    public String getLongitude() 
    {
        return longitude;
    }

    public void setLongitude(String longitude) 
    {
        this.longitude = longitude;
    }

}
