package com.shopKpr.apps.medicine.service;

import com.shopKpr.apps.medicine.model.MedicineAdmin;
import com.shopKpr.apps.medicine.model.MedicineModifiedShopKpr;
import com.shopKpr.apps.medicine.repositories.elastic.MedicineElasticRepoShopKpr;
import com.shopKpr.apps.medicine.repositories.mongo.MedicineRepoShopKpr;
import com.shopKpr.exceptions.MedicineNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ShopKprMedicineService {

    private final MedicineRepoShopKpr medicineRepoShopKpr;
    private final MedicineElasticRepoShopKpr medicineElasticRepoShopKpr;

    public ShopKprMedicineService( MedicineRepoShopKpr medicineRepoShopKpr,
                                   MedicineElasticRepoShopKpr medicineElasticRepoShopKpr ) {
        this.medicineRepoShopKpr = medicineRepoShopKpr;
        this.medicineElasticRepoShopKpr = medicineElasticRepoShopKpr;
    }


    public List<MedicineModifiedShopKpr> getAllMedicine( Integer pageNumber ) {

        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, "brandName");

        List<MedicineModifiedShopKpr> medicineModifiedShopKprList;

        try {
            medicineModifiedShopKprList = medicineElasticRepoShopKpr.findAll(pageable)
                    .get()
                    .map(medicine -> {

                        MedicineModifiedShopKpr medicineModifiedShopKpr = new MedicineModifiedShopKpr();

                        medicineModifiedShopKpr.setId(medicine.getId());
                        medicineModifiedShopKpr.setImageUrl(medicine.getImageUrl());
                        medicineModifiedShopKpr.setBrandName(medicine.getBrandName());
                        medicineModifiedShopKpr.setGenerics(medicine.getGenerics());

                        return medicineModifiedShopKpr;

                    }).collect(Collectors.toList());
        } catch (Exception exception) {
            log.error("Elastic search error, Error is: " + exception.getMessage());
            throw new RuntimeException(exception);
        }

        return medicineModifiedShopKprList;
    }

    public MedicineAdmin getMedicine( String medicineId ) {
        return medicineElasticRepoShopKpr.findById(medicineId)
                .stream().findFirst().orElseThrow(() -> {
                    log.info("MedicineAdmin with id: " + medicineId + "not found");
                    throw new MedicineNotFound("MedicineAdmin with id: " + medicineId + "not found");
                });
    }
}
