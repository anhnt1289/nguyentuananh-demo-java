package com.base.common.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class EditContentCommentRequest extends AddContentCommentRequest {
    @Schema( type = "long", example = "1")
    @NotNull
    private Long id;

    @Builder
    public EditContentCommentRequest(Long id, Long contentId, String comment, Integer isHidden, Integer isCreateByCreator) {
        super(contentId, comment, isHidden, isCreateByCreator);
        this.id = id;
    }
}
