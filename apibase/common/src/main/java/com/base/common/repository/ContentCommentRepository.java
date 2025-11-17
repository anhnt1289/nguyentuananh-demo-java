package com.base.common.repository;

import com.base.common.model.ContentComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface ContentCommentRepository extends JpaRepository<ContentComment, Long> {
}
