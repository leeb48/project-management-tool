package com.pmt.prjmanagetool.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashMap;

@Service
public class MapValidationErrorService {


    public ResponseEntity<?> MapValidationService(BindingResult result) {

        if (result.hasErrors()) {

            HashMap<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });

            return new ResponseEntity<HashMap<String, String>>(errors, HttpStatus.BAD_REQUEST);
        }

        return null;
    }

}
