package com.base.common.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddContentCommentRequest {
    @Schema( type = "long", example = "1")
    private Long contentId;
    @Schema( type = "string", example = "Lovely surprise bumping into Ken McGinley in Johnstone. The man is made of steel and is still going strong. Happy 81st birthday Ken!\uD83E\uDD73\uD83C\uDF89")
    private String comment;
    @Schema( type = "integer", example = "1")
    private Integer isHidden;
    @Schema( type = "integer", example = "1")
    private Integer isCreateByCreator;

    public AddContentCommentRequest(Long contentId, String comment, Integer isHidden, Integer isCreateByCreator) {
        this.contentId = contentId;
        this.comment = comment;
        this.isHidden = isHidden;
        this.isCreateByCreator = isCreateByCreator;
    }
}


