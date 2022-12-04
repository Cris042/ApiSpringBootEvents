package edu.ifgoiano.example.events.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import edu.ifgoiano.example.events.exceptions.others.NotFoundException;
import edu.ifgoiano.example.events.models.Events;
import edu.ifgoiano.example.events.models.Images;
import edu.ifgoiano.example.events.service.EventService;
import edu.ifgoiano.example.events.service.FileStorageService;
import edu.ifgoiano.example.events.service.ImageService;
import edu.ifgoiano.example.events.vo.UploadFileResponseVO;

import org.springframework.core.io.Resource;


@RestController
@RequestMapping("/api/event")
public class EventImageController 
{
    private Logger logger = Logger.getLogger( EventImageController.class.getName() );

    @Autowired
    private FileStorageService fileStorageService;
 
    @Autowired
    EventService eventService;

    @Autowired
    ImageService imageService;

    @PostMapping("/uploadFile/{id}")
    public UploadFileResponseVO uploadFile(@RequestParam("file") MultipartFile file, @PathVariable(value = "id") UUID id ) 
    {
        logger.info("Storingfile todisk");

        Optional<Events> obj = eventService.findByEventsID(id);

        if (!obj.isPresent()) 
        {
            throw new NotFoundException("Thing Not found!.");
        }

        Set<Images> image = new HashSet<>();

        var filename = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("/api/thing/downloadFile/")
        .path(filename)
        .toUriString();

        var imageEntities = new Images( filename, fileDownloadUri );

        imageService.save( imageEntities );
        image.add(imageEntities);
 
        var thingEntities = new Events();
        BeanUtils.copyProperties( obj.get(), thingEntities);  
        thingEntities.setImagens(image); 

        eventService.save( thingEntities );

                    
        return new UploadFileResponseVO(filename, fileDownloadUri, file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultFile")
    public List<UploadFileResponseVO> uploadMultFile(@RequestParam("files") MultipartFile[] files, @PathVariable(value = "id") UUID id) 
    {
        logger.info("Storingfiles todisk");

        return Arrays.asList(files).stream().map(file-> uploadFile(file, id)).collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename, HttpServletRequest request) 
    {
        Resource resource = fileStorageService.loadFileAsResource(filename);
        String contentType= "";

        try
        { 
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } 
        catch(Exception e)
        {
            logger.info("Could not determine file type!");
        }

        if(contentType.isBlank()) contentType= "application/octet-stream";

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
               .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+ resource.getFilename() + "\"").body(resource);

    }

}

