package com.base.common.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author : AnhNT
 * @since : 10/11/2021, Wed
 */
@Builder
public class LoginRequest {
    @Schema( type = "string", example = "xxxxx@gmail.com")
    @NotBlank
    @Email
    private String email;

    @Schema( type = "string", example = "123456789")
    @NotBlank
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
