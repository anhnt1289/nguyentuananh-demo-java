package com.base.common.payload;

import com.base.common.constant.UserRoleEnum;
import com.base.common.constant.UserStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author : AnhNT
 * @since : 10/11/2021, Wed
 */
@Builder
public class UserRequest {
    @Schema( type = "string", example = "Nguyen Van A")
    @NotBlank
    private String name;

    @Schema( type = "string", example = "xxxxx@gmail.com")
    @NotBlank
    @Email
    private String email;

    private Integer status = UserStatusEnum.ACTIVATE.intValue();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
