package com.pmt.prjmanagetool.payload;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {

    /*
    Object used to receive login info from the user(client side)
     */

    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
}
