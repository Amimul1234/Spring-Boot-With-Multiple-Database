package com.shopKpr.shopKprRegister.repository.elasticSearchRepo;

import com.shopKpr.shopKprRegister.model.ShopKprUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopKprRegElasticRepo extends ElasticsearchRepository<ShopKprUser, String> {
    Optional<ShopKprUser> findByMobileNumber( String mobileNumber );

    Page<ShopKprUser> findAllByEnabled( boolean enabled, Pageable pageable );

    @Query("{\"wildcard\":{\"fullName\":{\"value\":\"?0\",\"case_insensitive\":true}}}")
    Page<ShopKprUser> findShopKprByFullName( String name, Pageable pageable );
}
