package edu.ifgoiano.example.events.models;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Entity
public class Events 
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String payment;
    @Column(nullable = false)
    private LocalDateTime creationDate;
    @Column(nullable = false)
    private String date;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "events_athletic", joinColumns = @JoinColumn(name = "id_events"),inverseJoinColumns = @JoinColumn(name = "id_athletics"))
    private Set<Athletics> athletics = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "events_attraction", joinColumns = @JoinColumn(name = "id_events"),inverseJoinColumns = @JoinColumn(name = "id_attraction"))
    private Set<Attractions> attraction = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "events_imagen", joinColumns = @JoinColumn(name = "id_events"),inverseJoinColumns = @JoinColumn(name = "id_img"))
    private Set<Images> imagens = new HashSet<>();
    
    @ManyToOne
    @JoinColumn(name="id_category", nullable=false)
    private Categorys category;

    @ManyToOne
    @JoinColumn(name="id_place", nullable=false)
    private Places place;

    public Events()
    {

    }

    public Events
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
