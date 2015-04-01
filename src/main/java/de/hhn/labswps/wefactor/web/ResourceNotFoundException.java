package de.hhn.labswps.wefactor.web;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.NOT_FOUND)
public final class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;
}