package com.base.common.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "\"content\"")
public class Content extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"id\"", nullable = false)
    private Long id;

    @Column(name = "\"creator_id\"", nullable = false)
    private Long creator;

    @Size(max = 100)
    @Column(name = "\"content_title\"")
    private String contentTitle;

    @Column(name = "\"content\"")
    private String content;

    @Column(name = "\"thumbnail\"")
    private String thumbnail;

    @Column(name = "\"is_public\"")
    private Integer isPublic;

    public Content() {
    }
}
