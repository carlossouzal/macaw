package edu.maplewood.master_schedule.controller;

import jakarta.persistence.EntityNotFoundException;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(NoResourceFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public HashMap<String, String> handleNoHandlerFoundException(NoResourceFoundException ex) {
    LOGGER.warn(ex.getMessage(), ex);
    HashMap<String, String> response = new HashMap<>();
    response.put("type", "https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status/404");
    response.put("instance", "/resource-not-found");
    response.put("title", "Resource Not Found");
    response.put("detail", "The requested resource could not be found.");
    response.put("status", String.valueOf(HttpStatus.NOT_FOUND.value()));
    return response;
  }

  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public HashMap<String, String> handleEntityNotFoundException(EntityNotFoundException ex) {
    LOGGER.warn(ex.getMessage(), ex);
    HashMap<String, String> response = new HashMap<>();
    response.put("type", "https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status/404");
    response.put("instance", "/entity-not-found");
    response.put("title", "Entity Not Found");
    response.put("detail", ex.getMessage());
    response.put("status", String.valueOf(HttpStatus.NOT_FOUND.value()));
    return response;
  }
}
