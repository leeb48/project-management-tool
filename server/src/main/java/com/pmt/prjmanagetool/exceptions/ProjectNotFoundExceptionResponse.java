package com.pmt.prjmanagetool.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectNotFoundExceptionResponse {
    private String errorMessage;
}
