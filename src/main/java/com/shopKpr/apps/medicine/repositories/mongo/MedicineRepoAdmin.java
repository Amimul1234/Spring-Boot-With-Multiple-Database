package com.shopKpr.apps.medicine.repositories.mongo;

import com.shopKpr.apps.medicine.model.MedicineAdmin;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicineRepoAdmin extends
        org.springframework.data.mongodb.repository.MongoRepository<MedicineAdmin, String> {

    void deleteByBrandName( String brandName );

    Optional<MedicineAdmin> findByBrandName( String brandName );

}
