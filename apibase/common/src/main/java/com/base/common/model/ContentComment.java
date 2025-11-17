package com.base.common.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "\"content_comment\"")
public class ContentComment extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"id\"", nullable = false)
    private Long id;

    @Column(name = "\"content_id\"")
    private Long contentId;

    @Column(name = "\"creator_id\"")
    private Long creator;

    @Column(name = "\"comment\"")
    private String comment;

    @Column(name = "\"is_hidden\"")
    private Integer isHidden;

    @Column(name = "is_create_by_creator")
    private Integer isCreateByCreator;

    public ContentComment() {
    }
}
