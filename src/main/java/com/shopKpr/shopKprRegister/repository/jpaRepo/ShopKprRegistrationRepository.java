package com.shopKpr.shopKprRegister.repository.jpaRepo;

import com.shopKpr.shopKprRegister.model.ShopKpr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ShopKprRegistrationRepository extends JpaRepository<ShopKpr, String> {
    Optional<ShopKpr> findByMobileNumber( String mobileNumber);
}
