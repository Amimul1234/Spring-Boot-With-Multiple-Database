package com.shopKpr.shopKprRegister.repository.jpaRepo;

import com.shopKpr.shopKprRegister.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopAddingRepo extends JpaRepository<Shop, Long> {

}
