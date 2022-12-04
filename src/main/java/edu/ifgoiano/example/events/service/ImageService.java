package edu.ifgoiano.example.events.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ifgoiano.example.events.models.Images;
import edu.ifgoiano.example.events.repository.ImageRepository;


@Service
public class ImageService 
{
    @Autowired
    ImageRepository imageRepository;
    
    @Transactional
    public Images save(Images obj) 
    {
        return imageRepository.save( obj );
    }

}
