package edu.ifgoiano.example.events.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ifgoiano.example.events.models.Image;
import edu.ifgoiano.example.events.repository.ImageRepository;


@Service
public class ImageService 
{
    @Autowired
    ImageRepository imageRepository;
    
    @Transactional
    public Image save(Image obj) 
    {
        return imageRepository.save( obj );
    }

    public Optional<Image> findByImagename(String name) 
    {
        return imageRepository.findByName( name );
    }

    @Transactional
    public void delete(Image Image) 
    {
        imageRepository.delete(Image);
    }

    public List<Image> findAll()
    {
        return imageRepository.findAll();
    }

    public void deleteAll(List<Image> Image)
    {
        List<Image> allImage = imageRepository.findAll();
        imageRepository.deleteAll( allImage);
    }

}
