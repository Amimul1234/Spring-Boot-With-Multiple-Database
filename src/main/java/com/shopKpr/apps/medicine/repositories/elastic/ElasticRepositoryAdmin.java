package com.shopKpr.apps.medicine.repositories.elastic;

import com.shopKpr.apps.medicine.model.MedicineAdmin;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ElasticRepositoryAdmin extends ElasticsearchRepository<MedicineAdmin, String> {
    Optional<MedicineAdmin> findByBrandName( String brandName );
}
