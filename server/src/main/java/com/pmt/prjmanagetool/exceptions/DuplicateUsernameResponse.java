package com.pmt.prjmanagetool.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DuplicateUsernameResponse {
    private String message;
}
