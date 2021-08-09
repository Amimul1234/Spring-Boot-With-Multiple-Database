package com.shopKpr.admin.repository;

import com.shopKpr.admin.model.ShopKprAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRegisterRepo extends JpaRepository<ShopKprAdmin, String> {
    Optional<ShopKprAdmin> findByEmailAddress( String emailAddress );
}
