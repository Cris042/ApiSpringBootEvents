package edu.ifgoiano.example.events.models;

import javax.persistence.*;

import java.util.UUID;

@Entity
public class Participants 
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private UUID eventId;
    @Column(nullable = false)
    private UUID userId;

    
    public UUID getEventId() 
    {
        return eventId;
    }
    public void setEventId(UUID eventId) 
    {
        this.eventId = eventId;
    }

    public UUID getUserId() 
    {
        return userId;
    }
    public void setUserId(UUID userId) 
    {
        this.userId = userId;
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
