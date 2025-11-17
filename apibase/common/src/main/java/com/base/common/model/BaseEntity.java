package com.base.common.model;

import com.base.common.constant.UserStatusEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author : AnhNT
 * @since : 11/18/2021, Fri
 */
@Getter
@Setter
@MappedSuperclass
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // 0: no active
    // 1: active
    // -1: delete
    @Column(name = "status")
    protected Integer status = UserStatusEnum.ACTIVATE.intValue();

    @Column(name = "create_time", updatable= false)
    @CreatedDate
    protected LocalDateTime createTime;

    @Column(name = "update_time", insertable = false)
    @LastModifiedDate
    protected LocalDateTime updateTime;

    @PrePersist
    public void prePersist() {
        this.createTime = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updateTime = LocalDateTime.now();
    }

    public BaseEntity() {
    }

}
