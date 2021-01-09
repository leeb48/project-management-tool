package com.pmt.prjmanagetool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProjectBacklogNotFoundException extends RuntimeException {

    public ProjectBacklogNotFoundException(String message) {
        super(message);
    }

}
