package com.base.common.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddContentRequest {
    @Schema( type = "string", example = "Clark Kent")
    private String contentTitle;
    @Schema( type = "string", example = "Superman (real name Clark Kent, born Kal-El) is one of the last children of Krypton, sent as the dying planet's last hope to Earth, where he grew to become its kind, noble protector.")
    private String content;
    @Schema( type = "string", example = "https://static1.cbrimages.com/wordpress/wp-content/uploads/2020/06/Superman-Clark-Kent-Death-feature.jpg?q=50&fit=contain&w=1140&h=&dpr=1.5")
    private String thumbnail;
    @Schema( type = "integer", example = "1")
    private Integer isPublic;

    public AddContentRequest(String contentTitle, String content, String thumbnail, Integer isPublic) {
        this.contentTitle = contentTitle;
        this.content = content;
        this.thumbnail = thumbnail;
        this.isPublic = isPublic;
    }
}


