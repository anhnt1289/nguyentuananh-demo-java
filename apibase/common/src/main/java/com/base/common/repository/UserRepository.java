package com.base.common.repository;

import com.base.common.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : AnhNT
 * @since : 10/11/2021, Wed
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndStatus(String email, Integer status);

    Boolean existsByEmailAndStatus(String email, Integer status);

    Optional<User> findByProviderIdAndStatus(String providerId, Integer status);

    Page<User> findAllByStatus(Pageable pageable, Integer status);

    Page<User> findByStatusAndIdGreaterThanOrderByIdAsc(Integer status, Long id, Pageable pageable);

    Optional<User> findByIdAndStatus(Long id, Integer status);

    @Modifying
    @Transactional
    @Query(value = "update users set status = :status where id = :id", nativeQuery = true)
    void updateStatus(@Param(value = "id") long id, @Param(value = "status") Integer status);
}
