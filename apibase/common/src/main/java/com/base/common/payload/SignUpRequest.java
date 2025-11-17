package com.base.common.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author : AnhNT
 * @since : 10/11/2021, Wed
 */
@Setter
@Getter
public class SignUpRequest {
    @Schema( type = "string", example = "Nguyen Van A")
    @NotBlank
    private String name;

    @Schema( type = "string", example = "xxxxx@gmail.com")
    @NotBlank
    @Email
    private String email;

    @Schema( type = "string", example = "123456789")
    @NotBlank
    private String password;

    public SignUpRequest(@NotBlank String name, @NotBlank @Email String email, @NotBlank String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
