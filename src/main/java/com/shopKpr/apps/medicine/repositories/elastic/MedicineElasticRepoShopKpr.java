package com.shopKpr.apps.medicine.repositories.elastic;

import com.shopKpr.apps.medicine.model.MedicineAdmin;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineElasticRepoShopKpr extends ElasticsearchRepository<MedicineAdmin, String> {
}
