package com.base.common.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class EditContentRequest extends AddContentRequest {
    @Schema( type = "long", example = "1")
    @NotNull
    private Long id;

    @Builder
    public EditContentRequest(Long id, String contentTitle, String content, String thumbnail, Integer isPublic) {
        super(contentTitle, content, thumbnail, isPublic);
        this.id = id;
    }
}
