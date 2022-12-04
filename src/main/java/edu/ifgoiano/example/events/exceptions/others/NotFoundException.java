package edu.ifgoiano.example.events.exceptions.others;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException
{

    public NotFoundException(String ex )
    {
        super(ex);
    }

    private static final long serialVersionUID = 1L;
 
}
