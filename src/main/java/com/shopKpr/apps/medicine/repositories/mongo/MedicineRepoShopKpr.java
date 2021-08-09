package com.shopKpr.apps.medicine.repositories.mongo;

import com.shopKpr.apps.medicine.model.MedicineAdmin;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepoShopKpr extends org.springframework.data.mongodb.repository.MongoRepository<MedicineAdmin, String> {
}
