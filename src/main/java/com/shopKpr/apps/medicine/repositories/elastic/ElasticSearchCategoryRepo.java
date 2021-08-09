package com.shopKpr.apps.medicine.repositories.elastic;

import com.shopKpr.apps.medicine.model.Category;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElasticSearchCategoryRepo extends ElasticsearchRepository<Category, Long> {

}
