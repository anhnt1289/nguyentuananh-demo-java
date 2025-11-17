package com.base.common.repository;

import com.base.common.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface ContentRepository extends JpaRepository<Content, Long> {
}
